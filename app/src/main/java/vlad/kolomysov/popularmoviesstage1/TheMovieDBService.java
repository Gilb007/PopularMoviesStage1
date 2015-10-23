package vlad.kolomysov.popularmoviesstage1;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Copyright (C) Created by Vlad Kolomysov on 30.09.15.
 */

/**
 * interface TheMovieDBService with three methods
  Retrofit + RxJava
  public static final String SERVICE_ENDPOINT = "https://api.themoviedb.org/3/";
 */

public interface TheMovieDBService

{
    // for retrieving list of movie by number of page
    @GET("discover/movie")
    Observable<ListMoviesModel> getListFilm(@Query("api_key") String api_key, @Query("page") String page);

    // for retrieving list of movie sorted by most popular
    @GET("discover/movie")
    Observable<ListMoviesModel> getListFilmSortedByMostPopular(@Query("sort_by") String sort_by, @Query("api_key") String api_key);


    // for retrieving list of movie sorted by highest rated
    @GET("discover/movie")
    Observable<ListMoviesModel>  getListFilmSortedByHighestRated(@Query("sort_by") String sort_by, @Query("vote_count.gte") String vote_count
            ,@Query("api_key") String api_key);

}

