package com.booklisting.mitriyg.booklisting;

import java.util.ArrayList;

public class Book {
    private String title;
    private ArrayList<String> authors;

    public Book() {

    }

    public Book(String title, ArrayList<String> authors) {
        this.title = title;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }
}
