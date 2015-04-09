package com.kkcorps.bmltoolkitandroid.Utils;

import android.util.Log;
import android.util.Xml;

import com.kkcorps.bmltoolkitandroid.BasicLearningItem;
import com.kkcorps.bmltoolkitandroid.Constants;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by root on 7/4/15.
 */
public class XmlParser {

    private static List<BasicLearningItem> ItemsList = new ArrayList<BasicLearningItem>();
    private static String author, collectionTitle, title, description;

    public XmlParser(){

    }

    public static Document getDomElement(String FileName){
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


    public static void readXML(String FileName){
        Log.i("XmlParser","InsideReader");
        ItemsList.clear();
        Document XmlDoc = getDomElement(FileName);
        NodeList authorInfo = XmlDoc.getElementsByTagName("author");
        author = authorInfo.item(0).getFirstChild().getTextContent();
        Log.i("XmlParser","Author: "+author);

        NodeList titleInfo = XmlDoc.getElementsByTagName("title");
        collectionTitle = titleInfo.item(0).getTextContent();
        Log.i("XmlParser","Collection Title: "+collectionTitle);

        NodeList dataList = XmlDoc.getElementsByTagName("data");

        NodeList nodeList = dataList.item(0).getChildNodes();
        Node elem = null;
        Node child;
        Log.i("XmlParserItemsLength",String.valueOf(nodeList.getLength()));

        for(int i=0;i<nodeList.getLength();i++){
           elem = nodeList.item(i);
           if(elem.hasChildNodes()){
               Log.i("XmlParser","hasChildNodes");
               for(child=elem.getFirstChild();child!=null;child = child.getNextSibling()){
                   Log.i("XmlParser","InsideForLoop");
                   if(child.getNodeName().equals("item_title")){
                        title = child.getTextContent();
                   }else if(child.getNodeName().equals("item_description")){
                       description = child.getTextContent();
                   }
               }
           }
           BasicLearningItem newItem = new BasicLearningItem(title,description,author,collectionTitle,String.valueOf(i));
           ItemsList.add(newItem);
        }

        GlobalModelCollection.globalCollectionList = ItemsList;
    }

}
