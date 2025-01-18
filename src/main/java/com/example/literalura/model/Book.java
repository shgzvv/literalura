package com.example.literalura.model;

import com.example.literalura.model.data.AuthorData;
import com.example.literalura.model.data.BookData;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String languages;

    private Integer download_count;

    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
    private Author authors;

    public Book(BookData data) {
        this.title = data.title();
        this.languages = data.languages().get(0);
        this.download_count = data.download_count();
        this.authors = new Author(data.authors().get(0));
    }

    public Book(String title, List<AuthorData> authors, String languages, Integer integer) {
        this.title = title;
        this.authors = new Author(authors.get(0));
        this.languages = languages;
        this.download_count = integer;
    }

    @Override
    public String toString() {
        return "\n---------------------" +
                "\nTitle: " + title +
                "\nAuthor: " + authors.getName() +
                "\nLanguages: " + languages +
                "\nDownloads: " + download_count +
                "\n---------------------";
    }
}
