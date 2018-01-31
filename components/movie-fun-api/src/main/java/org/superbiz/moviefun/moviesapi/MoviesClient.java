package org.superbiz.moviefun.moviesapi;

import org.springframework.web.client.RestOperations;

import java.util.List;

public class MoviesClient {

    private final RestOperations restOperations;
    private final String endpoint;

    public MoviesClient(RestOperations restOperations, String endpoint) {
        this.restOperations = restOperations;
        this.endpoint = endpoint;
    }



    public MovieInfo find(Long id){
        MovieInfo movieInfo = restOperations.getForObject(endpoint + "/"+ id, MovieInfo.class);
        return movieInfo;
    }
    public void addMovie(MovieInfo movie){
        restOperations.postForEntity(endpoint, movie, MovieInfo.class);
    }

    public void updateMovie(MovieInfo movie){
        restOperations.put(endpoint, movie);
    }
    public void deleteMovie(MovieInfo movie){
        restOperations.delete(endpoint + "/"+ movie.getId());
    }
    public void deleteMovieId(long id){
        restOperations.delete(endpoint + "/"+ id);
    }
    public List<MovieInfo> getMovies(){
        return restOperations.getForObject(endpoint, List.class);
    }
    public List<MovieInfo> findAll(int firstResult, int maxResults){
       return restOperations.getForObject(endpoint + "/from/"+ firstResult +"/to/"+maxResults, List.class);
    }
    public int countAll(){
        return restOperations.getForObject(endpoint +"/count", Integer.class);
    }
    public int count(String field, String searchTerm){
        return restOperations.getForObject(endpoint +"/count/"+field+"/"+searchTerm, Integer.class);
    }
    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults){
        return restOperations.getForObject(endpoint +"/"+field+"/"+searchTerm+ "/from/"+ firstResult +"/to/"+maxResults, List.class);
    }
    public void clean(){
        restOperations.delete(endpoint +"/all");
    }


}
