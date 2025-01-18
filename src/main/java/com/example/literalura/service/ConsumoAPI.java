package com.example.literalura.service;

import com.example.literalura.model.Author;
import com.example.literalura.model.Book;
import com.example.literalura.model.Info;
import com.example.literalura.model.data.BookData;
import com.example.literalura.repository.AuthorRepository;
import com.example.literalura.repository.BookRepository;
import com.example.literalura.service.data.DataConvert;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumoAPI {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public ConsumoAPI(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }

    public Optional<BookData> getBook(String title) {
        String url = "https://gutendex.com/books/?search=" + title;
        System.out.println(url);
        var json = obtenerDatos(url);
        DataConvert dc = new DataConvert();
        var data = dc.convert(json, Info.class);

        Optional<BookData> searchBook = data.results()
                .stream()
                .filter(b -> b.title().toLowerCase().contains(title.toLowerCase()))
                .findFirst();

        return searchBook;
    }

    public void findBookByTitle (String title) {

        BookData bookData = getBook(title).orElseThrow();

        try {
            Author author = new Author(bookData.authors().get(0));
            authorRepository.save(author);

            Book book = new Book(bookData);
            book.setAuthors(author);
            System.out.println(book.toString());
            bookRepository.save(book);
        } catch (Exception e) {
            System.out.println(
                    e.getMessage()
            );
        }

    }

    public void findAllRegisteredBooks () {
        var books = bookRepository.findAll();
        books.forEach(book -> System.out.println(book.toString()));
    }

    public void findAllRegisteredAuthors () {
        var authors = authorRepository.findAll();
        authors.forEach(author -> System.out.println(author.toString()));
    }

    public void findAliveAuthors(int year) {
        var authors = authorRepository.findAll().stream()
                .filter(author -> author.getDeath_year() > year && author.getBirth_year() < year)
                .toList();

        if (authors.isEmpty()) {
            System.out.println("No authors found");
        } else {
            authors.forEach(author -> System.out.println(author.toString()));
        }
    }

    public void findBooksByLanguage(String language) {
        var books = bookRepository.findAllByLanguagesContaining(language);

        if (books.isEmpty()) {
            System.out.println("No books in " + language + " found");
        } else {
            books.forEach(book -> System.out.println(book.toString()));
        }
    }
}
