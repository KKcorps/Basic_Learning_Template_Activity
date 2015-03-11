package com.kkcorps.bmltoolkitandroid;

/**
 * Created by root on 9/3/15.
 */
public class BasicLearningItem {
    private String Title, Description, Author, CollectionTitle;
    private int NumberOfItems;

    public BasicLearningItem(String title, String description, String author, String collectionTitle, int numberOfItems){
        Title = title;
        Description = description;
        Author = author;
        CollectionTitle = collectionTitle;
        NumberOfItems = numberOfItems;
    }

    private String getTitle(){
        if(Title!=null || (!Title.isEmpty()) ){
            return Title;
        }else{
            return "";
        }
    }

    private String getDescription(){
        if(Description!=null || (!Description.isEmpty()) ){
            return Description;
        }else{
            return "";
        }
    }

    private String getAuthor(){
        if(Author!=null || (!Author.isEmpty()) ){
            return Author;
        }else{
            return "";
        }
    }

    private String getCollectionTitle(){
        if(CollectionTitle!=null || (!CollectionTitle.isEmpty()) ){
            return CollectionTitle;
        }else{
            return "";
        }
    }

    private int getNumberOfItems(){
        return NumberOfItems;
    }
}
