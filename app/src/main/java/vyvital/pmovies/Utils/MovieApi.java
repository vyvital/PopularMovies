package vyvital.pmovies.Utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vyvital.pmovies.MovieList;

public interface MovieApi {

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieList> getTopPopular(@Query("api_key") String apiKey);
}