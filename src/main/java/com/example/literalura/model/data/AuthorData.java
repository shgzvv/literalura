package com.example.literalura.model.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData (
        @JsonAlias("birth_year") int birth_year,
        @JsonAlias("death_year") int death_year,
        @JsonAlias("name") String name

) {
}
