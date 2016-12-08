package com.ycc.lottery;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.util.Xml;

import com.ycc.lottery.net.protocal.Message;
import com.ycc.lottery.net.protocal.element.CurrentIssueElement;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }

    public void testCreateXml() throws Exception {
        //序列化
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        serializer.startDocument(ConstantValue.ENCODING,null);
        Message message = new Message();

        message.serializerMessage(serializer);

        serializer.endDocument();
        Log.i(TAG,writer.toString());
    }

    public void testCreateXml2(){
        Message message = new Message();
        CurrentIssueElement ele = new CurrentIssueElement();
        ele.getLotteryid().setTagValue("118");

        String xml = message.getXml(ele);
        Log.i(TAG,xml);
    }
}