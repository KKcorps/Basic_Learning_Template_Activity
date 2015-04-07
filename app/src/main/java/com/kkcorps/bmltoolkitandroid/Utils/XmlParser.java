package com.kkcorps.bmltoolkitandroid.Utils;

import android.util.Log;

import com.kkcorps.bmltoolkitandroid.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by root on 7/4/15.
 */
public class XmlParser {

    public XmlParser(){

    }

    private static Document getDomElement(String FileName){
        Document doc = null;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            doc= documentBuilder.parse(new File(Constants.DATA_BASE_DIRECTORY+"/"+FileName));

        }catch (Exception e){
            e.printStackTrace();
        }
        return doc;
    }

    public static void readXML(String FileName){
        Log.i("XmlParser","InsideReader");
        Document XmlDoc = getDomElement(FileName);
        NodeList nodeList = XmlDoc.getElementsByTagName("item");
        Node elem = null;
        Node child;
        Log.i("XmlParser",String.valueOf(nodeList.getLength()));
        for(int i=0;i<nodeList.getLength();i++){
           elem = nodeList.item(i);
            Log.i("XmlParser","insideReader");
           if(elem.hasChildNodes()){
               for(child=elem.getFirstChild();child!=null;child = child.getNextSibling()){
                   if(child.getNodeType()==Node.TEXT_NODE){
                       Log.i("XmlParser",child.getNodeValue());
                   }
               }
           }
        }
    }

}
