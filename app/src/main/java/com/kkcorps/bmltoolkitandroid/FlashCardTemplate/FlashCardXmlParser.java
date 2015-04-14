package com.kkcorps.bmltoolkitandroid.FlashCardTemplate;

import android.util.Log;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;
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
public class FlashCardXmlParser {

    private static List<Model> ItemsList = new ArrayList<Model>();
    private static String question, answer, hint, base64image;

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
                    if(child.getNodeName().equals("question")){
                        question = child.getTextContent();
                    }else if(child.getNodeName().equals("answer")){
                        answer = child.getTextContent();
                    }else if(child.getNodeName().equals("hint")){
                        hint = child.getTextContent();
                    }else if(child.getNodeName().equals("image")){
                        base64image = child.getTextContent();
                    }
                }
            }
            FlashCardItem newCard = new FlashCardItem(question,answer,hint,author,String.valueOf(i+1),collectionTitle,base64image);
            ItemsList.add(newCard);
        }
        return ItemsList;
    }
}
