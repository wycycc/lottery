package com.ycc.lottery;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void createXml() throws Exception {
        //序列化
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        serializer.startDocument(ConstantValue.ENCODING,null);

        serializer.startTag(null,"message");
        serializer.startTag(null,"header");

        serializer.startTag(null,"agenterid");
        serializer.text(ConstantValue.AGENTERID);
        serializer.endTag(null,"agenterid");

        serializer.endTag(null,"header");
        serializer.startTag(null,"body");
        serializer.endTag(null,"body");

        serializer.endTag(null,"message");
        serializer.endDocument();
    }
}