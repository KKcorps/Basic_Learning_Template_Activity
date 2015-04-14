package com.kkcorps.bmltoolkitandroid.Utils;

import android.util.Log;
import android.util.Xml;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningGenerator;
import com.kkcorps.bmltoolkitandroid.Constants;
import com.kkcorps.bmltoolkitandroid.FlashCardTemplate.FlashCardGenerator;
import com.kkcorps.bmltoolkitandroid.GlobalModelCollection;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

/**
 * Created by root on 14/4/15.
 */
public class XmlGenerator {

    static boolean isFileNameSet = true;

    public static File writeXML(String fileName, Constants.Templates templates){
        if(fileName==null || fileName.isEmpty()){
            isFileNameSet = false;
        }
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();

        try {

            xmlSerializer.setOutput(stringWriter);

            switch (templates) {
                case INFO:
                    writeXMLHeaders(xmlSerializer,"InfoTemplate");
                    BasicLearningGenerator.writeXMLData(xmlSerializer);
                    if(!isFileNameSet) fileName = "info_content.xml";
                    break;
                case FLASHCARDS:
                    writeXMLHeaders(xmlSerializer, "FlashCardsTemplate");
                    FlashCardGenerator.writeXMLData(xmlSerializer);
                    if(!isFileNameSet) fileName = "flash_content.xml";
                    break;
                default:
                    Log.i("Generator", "Unknown Template!");
            }

            writeXmlFooter(xmlSerializer);
            return writeXMLToFile(stringWriter.toString(), fileName);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void writeXMLHeaders(XmlSerializer xmlSerializer, String templateName) throws IOException{
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag("", "buildmlearn_application");
            xmlSerializer.attribute("", "type", templateName);
            xmlSerializer.startTag("", "author");
            xmlSerializer.startTag("", "name");
            xmlSerializer.text(GlobalModelCollection.globalCollectionList.get(0).getAuthor());
            xmlSerializer.endTag("", "name");
            xmlSerializer.startTag("", "email");
            xmlSerializer.text("");
            xmlSerializer.endTag("", "email");
            xmlSerializer.endTag("", "author");
            xmlSerializer.startTag("", "title");
            xmlSerializer.text(GlobalModelCollection.globalCollectionList.get(0).getCollectionTitle());
            xmlSerializer.endTag("", "title");
            xmlSerializer.startTag("", "description");
            xmlSerializer.endTag("", "description");
            xmlSerializer.startTag("", "version");
            xmlSerializer.text("2");
            xmlSerializer.endTag("", "version");

    }
    public static void writeXmlFooter(XmlSerializer xmlSerializer) throws IOException{
        xmlSerializer.endTag("","buildmlearn_application");
        xmlSerializer.endDocument();
    }
    public static File writeXMLToFile(String data, String fileName) {
        try {
            File xmlFile2 = new File(Constants.DATA_BASE_DIRECTORY+"/assets/");
            xmlFile2.mkdir();
            File xmlFile = new File(Constants.DATA_BASE_DIRECTORY+"/assets/",fileName);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(xmlFile));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            return xmlFile;
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return null;
    }
}
