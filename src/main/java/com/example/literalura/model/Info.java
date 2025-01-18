package com.example.literalura.model;

import com.example.literalura.model.data.BookData;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Info(
        @JsonAlias("results") List<BookData> results
) {
}
