package vlad.kolomysov.popularmoviesstage1;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by admin on 30.09.15.
 */
// 1
// ретрофит сервис
// запрос идет на https://api.themoviedb.org/3/discover/movie?api_key=7c6c89132a5ebf7839844ad8427621ee&page=2 - получить список фильмов
// public static final String SERVICE_ENDPOINT = "https://api.themoviedb.org/3/";
public interface TheMovieDBService

{
    // for retrieving list of movie by number of page
    @GET("discover/movie")
    Observable<ListMoviesModel> getListFilm(@Query("api_key") String api_key, @Query("page") String page);

    // for retrieving list of movie sorted by most popular or highest rated
    @GET("discover/movie")
    Observable<ListMoviesModel> getListFilmSortedBy(@Query("sort_by") String sort_by, @Query("api_key") String api_key);
}

