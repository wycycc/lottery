package com.ycc.lottery;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.util.Xml;

import com.ycc.lottery.bean.User;
import com.ycc.lottery.engine.UserEngine;
import com.ycc.lottery.engine.user.UserEngineImpl;
import com.ycc.lottery.net.NetUtil;
import com.ycc.lottery.net.protocal.Message;
import com.ycc.lottery.net.protocal.element.CurrentIssueElement;
import com.ycc.lottery.utils.BeanFactory;

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

    public void testNetType(){
        NetUtil.checkNet(getContext());
    }

    public void testLogin(){
         UserEngineImpl impl=new UserEngineImpl();
         //UserEngineImpl1
         User user=new User();
         user.setUsername("13200000000");
         user.setPassword("0000000");
         Message login = impl.login(user);
         Log.i(TAG, login.getBody().getOelement().getErrorcode());
    }

    public void testUserLogin() {
        // UserEngineImpl impl=new UserEngineImpl();
        // UserEngineImpl1
        // User user=new User();
        // user.setUsername("13200000000");
        // user.setPassword("0000000");
        // Message login = impl.login(user);
        // Log.i(TAG, login.getBody().getOelement().getErrorcode());

        UserEngine engine = BeanFactory.getImpl(UserEngine.class);

        User user = new User();
        user.setUsername("13200000000");
        user.setPassword("0000000");
        Message login = engine.login(user);
        Log.i(TAG, login.getBody().getOelement().getErrorcode());
    }
}