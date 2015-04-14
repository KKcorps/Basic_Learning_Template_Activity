package com.kkcorps.bmltoolkitandroid.FlashCardTemplate;

import com.kkcorps.bmltoolkitandroid.Model;

/**
 * Created by root on 13/4/15.
 */
public class FlashCardItem extends Model{
    String Question, Answer, Hint, Author, NumberOfItems, CollectionTitle, Base64Image;

    public FlashCardItem(String question, String answer, String hint, String author, String numberOfItems, String collectionTitle, String base64Image ){
        Question = question;
        Answer = answer;
        Hint = hint;
        Author = author;
        NumberOfItems = numberOfItems;
        CollectionTitle = collectionTitle;
        Base64Image = base64Image;
    }

    public FlashCardItem() {
    }

    @Override
    public String getTitle() {
        return Question;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getHint() {
        return Hint;
    }

    public void setHint(String hint) {
        Hint = hint;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getNumberOfItems() {
        return NumberOfItems;
    }

    public void setNumberOfItems(String numberOfItems) {
        NumberOfItems = numberOfItems;
    }

    public String getCollectionTitle() {
        return CollectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        CollectionTitle = collectionTitle;
    }

    public String getBase64Image() {
        return Base64Image;
    }

    public void setBase64Image(String base64Image) {
        Base64Image = base64Image;
    }

    @Override
    public String getName() {
        return this.toString();
    }
}
