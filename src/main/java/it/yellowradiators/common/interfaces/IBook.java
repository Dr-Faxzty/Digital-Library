package it.yellowradiators.common.interfaces;

import java.time.LocalDate;

public interface IBook {
    String getIsbn();
    String getTitle();
    String getAuthor();
    LocalDate getDate();
    String getType();
    boolean available();
    String getUrlImage();

    void setIsbn(String isbn);
    void setTitle(String title);
    void setAuthor(String author);
    void setDate(LocalDate date);
    void setType(String type);
    void setAvailable(boolean available);
    void setUrlImage(String urlImage);

    boolean isNull();
}
