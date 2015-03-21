package com.kkcorps.bmltoolkitandroid;

/**
 * Created by root on 9/3/15.
 */
public class BasicLearningItem {
    private String Title, Description, Author, CollectionTitle, NumberOfItems;

    public BasicLearningItem(String title, String description, String author, String collectionTitle, String numberOfItems){
        Title = title;
        Description = description;
        Author = author;
        CollectionTitle = collectionTitle;
        NumberOfItems = numberOfItems;
    }

    public String getTitle(){
        if(Title!=null || (!Title.isEmpty()) ){
            return Title;
        }else{
            return "";
        }
    }

    public String getDescription(){
        if(Description!=null || (!Description.isEmpty()) ){
            return Description;
        }else{
            return "";
        }
    }

    public String getAuthor(){
        if(Author!=null || (!Author.isEmpty()) ){
            return Author;
        }else{
            return "";
        }
    }

    public String getCollectionTitle(){
        if(CollectionTitle!=null || (!CollectionTitle.isEmpty()) ){
            return CollectionTitle;
        }else{
            return "";
        }
    }

    public String getNumberOfItems(){
        if(NumberOfItems!=null || (!NumberOfItems.isEmpty()) ){
            return NumberOfItems;
        }else{
            return "";
        }
    }
}
