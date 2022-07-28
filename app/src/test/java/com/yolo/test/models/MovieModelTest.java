package com.yolo.test.models;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MovieModelTest {

    public static final Integer MOVIE_ID = 456;
    public static final Integer MOVIE_ID_2 = 982;

    /*
        Compare two same movies
     */
    @Test
    void isMoviesEqual_identicalProperties_returnTrue() throws Exception {

        // Arrange
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(1);
        genreIds.add(2);
        MovieResult movie = new MovieResult("OverView",
                "release date",
                "tagline",
                "title",
                false,
                "asdasd",
                genreIds,
                "original",
                "asdasd",
                "asdasd",
                1,
                MOVIE_ID,
                23,
                4.44,
                false,
                4.34,
                "media",
                "firstAirDate",
                "originalName",
                null,
                "asdsad"
        );

        MovieResult movie1 = new MovieResult("OverView",
                "release date",
                "tagline",
                "title",
                false,
                "asdasd",
                genreIds,
                "original",
                "asdasd",
                "asdasd",
                1,
                MOVIE_ID,
                23,
                4.44,
                false,
                4.34,
                "media",
                "firstAirDate",
                "originalName",
                null,
                "asdsad"
        );


        // Act


        // Assert
        assertEquals(movie, movie1);
        System.out.println("The movies are equal!");
    }



    /*
        Compare movies with 2 different ids
     */

    @Test
    void isNotesEqual_differentIds_returnFalse() throws Exception {
        // Arrange
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(1);
        genreIds.add(2);
        MovieResult movie = new MovieResult("OverView",
                "release date",
                "tagline",
                "title",
                false,
                "asdasd",
                genreIds,
                "original",
                "asdasd",
                "asdasd",
                1,
                MOVIE_ID,
                23,
                4.44,
                false,
                4.34,
                "media",
                "firstAirDate",
                "originalName",
                null,
                "asdsad"
        );

        MovieResult movie1 = new MovieResult("OverView",
                "release date",
                "tagline",
                "title",
                false,
                "asdasd",
                genreIds,
                "original",
                "asdasd",
                "asdasd",
                1,
                MOVIE_ID_2,
                23,
                4.44,
                false,
                4.34,
                "media",
                "firstAirDate",
                "originalName",
                null,
                "asdsad"
        );


        // Act


        // Assert
        assertNotEquals(movie, movie1);
        System.out.println("The movies are not equal!");
    }


}


















