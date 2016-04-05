package com.tan.myapplication;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/3/31.
 */
public class MyHandler extends DefaultHandler {

    public List<App> list = new ArrayList<App>();

    private App app;
    private StringBuffer sb = new StringBuffer();




    @Override
    public void startDocument() throws SAXException {


    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {


        sb.setLength(0);
        if("app".equals(localName)){
            app = new App();

        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        sb.append(ch, start, length);



    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String data = sb.toString();
        if("id".equals(localName)){
            app.setId(data);

        }
        else if("name".equals(localName)){

            app.setName(data);

        }

        else if("version".equals(localName)){
            app.setVersion(data);

        }
        if("app".equals(localName)){
            list.add(app);

        }
    }



    @Override
    public void endDocument() throws SAXException {


    }

    public List<App> getList(){
        for(App i : list){
            Log.d("id", i.getId());
            Log.d("name",i.getName());
            Log.d("version",i.getVersion());
        }
        return list;
    }


}
