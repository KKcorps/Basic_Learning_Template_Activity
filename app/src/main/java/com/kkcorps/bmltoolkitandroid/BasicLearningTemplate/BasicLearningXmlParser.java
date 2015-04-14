package com.kkcorps.bmltoolkitandroid.BasicLearningTemplate;

import android.util.Log;

import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.Model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14/4/15.
 */
public class BasicLearningXmlParser {
    private static List<Model> ItemsList = new ArrayList<Model>();
    private static String title, description;

    public static List<Model> readData(Document xmlDoc, String author, String collectionTitle){
        //avoid reloading of previous project items.
        ItemsList.clear();
        NodeList dataList = xmlDoc.getElementsByTagName("data");
        NodeList nodeList = dataList.item(0).getChildNodes();
        Node elem = null;
        Node child;
        Log.i("XmlParserItemsLength", String.valueOf(nodeList.getLength()));

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
            BasicLearningItem newItem = new BasicLearningItem(title,description,author,collectionTitle,String.valueOf(i+1));
            ItemsList.add(newItem);
        }
        return ItemsList;
    }
}
