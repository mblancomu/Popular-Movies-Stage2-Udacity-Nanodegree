package com.example.blancomm.popularmoviesstage2.model;

/**
 * Created by manuel on 25/10/15.
 */
public class FavoriteInfo {

    private String idMovie;
    private String title;
    private int isChecked;

    public FavoriteInfo (String id, String title, int isChecked){
        this.idMovie = id;
        this.title = title;
        this.isChecked = isChecked;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }
}
