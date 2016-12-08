package com.ycc.lottery.net.protocal;

import android.util.Xml;

import com.ycc.lottery.ConstantValue;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

/**
 * 协议封装
 * Created by Administrator on 2016/12/8.
 */
public class Message {
    private Header header = new Header();
    private Body body = new Body();



    public Header getHeader() {
        return header;
    }

    public Body getBody() {
        return body;
    }

    /**
     * 序列化协议
     */
    public void serializerMessage(XmlSerializer serializer) {
        try {
            // <message version="1.0">
            serializer.startTag(null, "message");
            // MUST follow a call to startTag() immediately
            serializer.attribute(null, "version", "1.0");

            header.serializerHeader(serializer, body.getWholeBody());// 获取完整的body
//			body.serializerBody(serializer);
            serializer.startTag(null, "body");
            serializer.text(body.getBodyInsideDESInfo());
            serializer.endTag(null, "body");

            serializer.endTag(null, "message");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求的xml文件
     *
     * @return
     */
    public String getXml(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("element is null");
        }
        // 请求标示需要设置，请求内容需要设置
        header.getTransactiontype().setTagValue(element.getTransactionType());
        body.getElements().add(element);

        // 序列化
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        // This method can only be called just after setOutput
        try {
            serializer.setOutput(writer);
            serializer.startDocument(ConstantValue.ENCONDING, null);
            this.serializerMessage(serializer);
            serializer.endDocument();

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    // 需要处理的问题：
    // ①MD5处理时需要完整的明文body
    // ②请求标示需要设置，请求内容需要设置
    // ③加密body里面的数据

    // ④Element通用

}