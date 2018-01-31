package org.superbiz.moviefun.movies;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesBean moviesBean;

    public MoviesController(MoviesBean moviesBean) {
        this.moviesBean = moviesBean;
    }


    @GetMapping
    public List<Movie> getAllMovies(){
        return moviesBean.getMovies();
    }

    @PostMapping
    public void createMovie(@RequestBody Movie movie){
        moviesBean.addMovie(movie);
    }

    @PutMapping
    public void updateMovie(@RequestBody Movie movie){
        moviesBean.updateMovie(movie);
    }

    @GetMapping("/{id}")
    public Movie findMovie(@PathVariable("id") long id){
        return moviesBean.find(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable("id") long id){
        moviesBean.deleteMovieId(id);
    }

    @GetMapping("/from/{first}/to/{last}")
    public List<Movie> findRange(@PathVariable("first") int first, @PathVariable("last") int last){
        return moviesBean.findAll(first, last);
    }

    @GetMapping("/count")
    public int getMoviesCount(){
        return moviesBean.countAll();
    }

    @GetMapping("/count/{field}/{value}")
    public int getSpecficMovieCount(@PathVariable("field") String field, @PathVariable("value") String value){
        return moviesBean.count(field, value);
    }

    @GetMapping("/{field}/{value}/from/{first}/to/{last}")
    public List<Movie> getMoviesinRange(@PathVariable("field") String field, @PathVariable("value") String value, @PathVariable("first") int first, @PathVariable("last") int last){
        return moviesBean.findRange(field, value,first,last);
    }

    @DeleteMapping("/all")
    public void deleteAll(){
        moviesBean.clean();
    }

}
