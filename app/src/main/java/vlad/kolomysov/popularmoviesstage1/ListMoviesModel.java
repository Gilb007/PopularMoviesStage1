package vlad.kolomysov.popularmoviesstage1;


import java.util.List;

/**
 * Copyright (C) Created by Vlad Kolomysov on 30.09.15.
 */
// 2
// Model for result that we will retrieve from IMDB server
    // ListMoviesModel contains results, that contait list on Movies
public class ListMoviesModel
{
    String page;
    List<Film> results;
    String total_pages;
    String total_results;
}
