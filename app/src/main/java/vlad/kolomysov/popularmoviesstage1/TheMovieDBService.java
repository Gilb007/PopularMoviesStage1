package vlad.kolomysov.popularmoviesstage1;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by admin on 30.09.15.
 */
public interface TheMovieDBService {

    @GET("discover/movie")
    Observable<ListMoviesModel> getListFilm(@Query("api_key") String api_key, @Query("page") String page);


}
