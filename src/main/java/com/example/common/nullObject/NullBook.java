package com.example.common.nullObject;

import com.example.common.interfaces.IBook;

import java.time.LocalDate;

public class NullBook implements IBook {

    @Override public String getIsbn(){return " ";};
    @Override public String getTitle(){return " ";};
    @Override public String getAuthor(){return " ";};
    @Override public LocalDate getDate(){return null;};
    @Override public String getType(){return " ";};
    @Override public boolean available(){return false;};
    @Override public String getUrlImage(){return " ";};

    @Override public void setIsbn(String isbn){};
    @Override public void setTitle(String title){};
    @Override public void setAuthor(String author){};
    @Override public void  setDate(LocalDate date){};
    @Override public void setType(String type){};
    @Override public void setAvailable(boolean available){};
    @Override public void setUrlImage(String urlImage){};

    @Override public boolean isNull(){return true;};

    @Override
    public String toString() { return ""; }
}