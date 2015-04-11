package com.kkcorps.bmltoolkitandroid.BasicLearningTemplate;

import android.util.Log;
import android.util.Xml;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;
import com.kkcorps.bmltoolkitandroid.Constants;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by root on 6/4/15.
 */
public class BasicLearningGenerator {

    public static void writeXML(String fileName){
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try {
            xmlSerializer.setOutput(stringWriter);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("","buildmlearn_application");
            xmlSerializer.attribute("", "type", "InfoTemplate");
            xmlSerializer.startTag("", "author");
            xmlSerializer.startTag("","name");
            xmlSerializer.text(GlobalModelCollection.globalCollectionList.get(0).getAuthor());
            xmlSerializer.endTag("", "name");
            xmlSerializer.startTag("","email");
            xmlSerializer.text("");
            xmlSerializer.endTag("","email");
            xmlSerializer.endTag("","author");
            xmlSerializer.startTag("","title");
            xmlSerializer.text(GlobalModelCollection.globalCollectionList.get(0).getCollectionTitle());
            xmlSerializer.endTag("","title");
            xmlSerializer.startTag("","description");
            xmlSerializer.endTag("","description");
            xmlSerializer.startTag("","version");
            xmlSerializer.text("2");
            xmlSerializer.endTag("","version");

            //Inserting Data
            xmlSerializer.startTag("","data");
            List<BasicLearningItem> gdList = GlobalModelCollection.globalCollectionList;
            for(int i=0;i<gdList.size();i++){
                xmlSerializer.startTag("","item");

                xmlSerializer.startTag("","item_title");
                xmlSerializer.text(gdList.get(i).getTitle());
                xmlSerializer.endTag("","item_title");

                xmlSerializer.startTag("","item_description");
                xmlSerializer.text(gdList.get(i).getDescription());
                xmlSerializer.endTag("","item_description");

                xmlSerializer.endTag("","item");
            }
            xmlSerializer.endTag("","data");
            xmlSerializer.endTag("","buildmlearn_application");
            xmlSerializer.endDocument();

        }catch (Exception e){
            e.printStackTrace();
        }
        writeToFile(stringWriter.toString(), fileName);
    }

    private static void writeToFile(String data, String fileName) {
        try {
            File xmlFile2 = new File(Constants.DATA_BASE_DIRECTORY+"/assets/");
            xmlFile2.mkdir();
            File xmlFile = new File(Constants.DATA_BASE_DIRECTORY+"/assets/",fileName);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(xmlFile));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


}
