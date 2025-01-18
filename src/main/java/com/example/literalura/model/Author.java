package com.example.literalura.model;

import com.example.literalura.model.data.AuthorData;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonAlias("birth_year")
    private int birth_year;

    @JsonAlias("death_year")
    private int death_year;

    @JsonAlias("name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "authors", cascade = CascadeType.MERGE )
    @JsonManagedReference(
        value = "author-books"
    )
    @JsonIgnore
    private List<Book> books;

    public Author(AuthorData data) {
        this.birth_year = data.birth_year();
        this.death_year = data.death_year();
        this.name = data.name();
    }

    @Override
    public String toString() {
        List<String> books = this.getBooks().stream().map(Book::getTitle).toList();
        return "\n---------------------" +
                "\nName: " + name +
                "\nBirth year: " + birth_year +
                "\nDeath year: " + death_year +
                "\nBooks: " + books +
                "\n---------------------";
    }
}
