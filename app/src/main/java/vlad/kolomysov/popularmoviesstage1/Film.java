package vlad.kolomysov.popularmoviesstage1;

import java.util.List;

/**
 * Copyright (C) Created by Vlad Kolomysov on 30.09.15.
 */
// 2
// Model for one Movie
// Movie = Film
public class Film
{
    String adult;
    String backdrop_path;
    Integer[] genre_ids;
    String id;
    String original_language;
    String original_title;
    String overview;
    String popularity;
    String poster_path;
    String release_date;
    String title;
    String video;
    String vote_average;
    String vote_count;

    public String getPosterpath() {
        return poster_path;
    }
}
