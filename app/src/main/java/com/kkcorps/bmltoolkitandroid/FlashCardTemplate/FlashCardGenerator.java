package com.kkcorps.bmltoolkitandroid.FlashCardTemplate;

import android.util.Xml;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;
import com.kkcorps.bmltoolkitandroid.Model;
import com.kkcorps.bmltoolkitandroid.Utils.XmlGenerator;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by root on 14/4/15.
 */
public class FlashCardGenerator {

    public static void writeXMLData(XmlSerializer xmlSerializer) throws IOException{

            //Insert Data
            xmlSerializer.startTag("","data");
            List<Model> gdList = GlobalModelCollection.globalCollectionList;
            for(int i=0;i<gdList.size();i++){
                xmlSerializer.startTag("","item");

                FlashCardItem item = (FlashCardItem) gdList.get(i);

                xmlSerializer.startTag("","question");
                xmlSerializer.text(item.getQuestion());
                xmlSerializer.endTag("","question");

                xmlSerializer.startTag("","answer");
                xmlSerializer.text(item.getAnswer());
                xmlSerializer.endTag("","answer");

                xmlSerializer.startTag("","hint");
                xmlSerializer.text(item.getHint());
                xmlSerializer.endTag("","hint");

                xmlSerializer.startTag("","image");
                xmlSerializer.text(item.getBase64Image());
                xmlSerializer.endTag("","image");

                xmlSerializer.endTag("","item");
            }
            xmlSerializer.endTag("","data");

    }
}
