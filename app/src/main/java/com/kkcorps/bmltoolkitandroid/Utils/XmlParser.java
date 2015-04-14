package com.kkcorps.bmltoolkitandroid.Utils;

import android.util.Log;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;
import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningXmlParser;
import com.kkcorps.bmltoolkitandroid.Constants;
import com.kkcorps.bmltoolkitandroid.FlashCardTemplate.FlashCardXmlParser;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by root on 7/4/15.
 */
public class XmlParser {

    private static List<Model> ItemsList = new ArrayList<Model>();
    private static String author, collectionTitle;
    private static Constants.Templates template = Constants.Templates.INFO;
    private static String TAG="XmlParser";
    
    public XmlParser(){

    }

    private static Document getDomElement(String FileName){
        Document doc = null;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            doc= documentBuilder.parse(new File(FileName));

        }catch (Exception e){
            e.printStackTrace();
        }
        return doc;
    }


    public static String readXML(String FileName){
        Log.i(TAG,"InsideReader");
        Document xmlDoc = getDomElement(FileName);

        String templateName = readXMLHeaders(xmlDoc);

        switch (template){
            case INFO:
                ItemsList = BasicLearningXmlParser.readData(xmlDoc,author,collectionTitle);
                break;
            case FLASHCARDS:
                ItemsList = FlashCardXmlParser.readData(xmlDoc,author,collectionTitle);
                break;
            default:
                Log.i(TAG, "Unknown Template Found");
        }

        GlobalModelCollection.update(ItemsList);
        return templateName;
    }

    private static String readXMLHeaders(Document xmlDoc){
        //Determine the template
        NodeList startTag = xmlDoc.getElementsByTagName("buildmlearn_application");
        Node templateAttribute = startTag.item(0).getAttributes().getNamedItem("type");
        String templateName = templateAttribute.getTextContent();
        String TemplateString = templateName.substring(0, templateName.length()-8);
        template = Constants.Templates.valueOf(TemplateString.toUpperCase());

        Log.i(TAG,"Template Name: "+TemplateString);
        NodeList authorInfo = xmlDoc.getElementsByTagName("author");
        author = authorInfo.item(0).getFirstChild().getTextContent();
        Log.i(TAG,"Author: "+author);

        NodeList titleInfo = xmlDoc.getElementsByTagName("title");
        collectionTitle = titleInfo.item(0).getTextContent();
        Log.i(TAG,"Collection Title: "+collectionTitle);

        return TemplateString;
    }


}
