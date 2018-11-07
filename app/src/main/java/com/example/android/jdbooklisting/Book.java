package com.example.android.jdbooklisting;

public class Book {
    private String Kind;
    private String Title;
    private String Author;
    private String InfoLink;
    private String thumbNail;
    public Book(String kind,String title,String author,String infoLink,String thumbnail){
       Kind = kind;
       Title = title;
       Author = author;
       InfoLink = infoLink;
     thumbNail = thumbnail;
    }

    public String getKind(){
        return Kind;
    }

    public String getAuthor() {
        return Author;
    }

    public String getTitle() {
        return Title;
    }

    public String getInfoLink(){ return InfoLink; }

    public String getThumbNail(){return thumbNail;
    }
}
