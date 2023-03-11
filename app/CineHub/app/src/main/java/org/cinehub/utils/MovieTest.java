package org.cinehub.utils;

import java.util.ArrayList;
import java.util.List;

public class MovieTest {

    private String title;
    private String description;
    private String releaseDate;

    public MovieTest(String title, String description, String releaseDate) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "MovieTest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
