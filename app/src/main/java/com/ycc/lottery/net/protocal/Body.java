package com.ycc.lottery.net.protocal;

import android.util.Xml;

import com.ycc.lottery.ConstantValue;
import com.ycc.lottery.utils.DES;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息体结点封装
 * Created by Administrator on 2016/12/8.
 */
public class Body {
    private List<Element> elements=new ArrayList<Element>();

    /*********************处理服务器回复*************************/
    private String serviceBodyInsideDESInfo;//服务器端回复的body中的DES加密的信息
    private Oelement oelement=new Oelement();

    public Oelement getOelement() {
        return oelement;
    }
    public String getServiceBodyInsideDESInfo() {
        return serviceBodyInsideDESInfo;
    }
    public void setServiceBodyInsideDESInfo(String serviceBodyInsideDESInfo) {
        this.serviceBodyInsideDESInfo = serviceBodyInsideDESInfo;
    }
    /*********************处理服务器回复*************************/



    public List<Element> getElements() {
        return elements;
    }
    /**
     * 序列化请求
     */
    public void serializerBody(XmlSerializer serializer) {
        /**
         * <body>
         <elements>
         <element>
         <lotteryid>118</lotteryid>
         <issues>1</issues>
         </element>
         </elements>
         </body>
         */

        try {
            serializer.startTag(null, "body");
            serializer.startTag(null, "elements");

            for(Element item:elements){
                item.serializerElement(serializer);
            }

            serializer.endTag(null, "elements");
            serializer.endTag(null, "body");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取到完整的body
     * @return
     */
    public String getWholeBody()
    {
        StringWriter writer=new StringWriter();

        XmlSerializer temp= Xml.newSerializer();
        try {
            temp.setOutput(writer);
            this.serializerBody(temp);
            // output will be flushed
            temp.flush();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取body里面的DES加密数据
     * @return
     */
    public String getBodyInsideDESInfo()
    {
        // 加密数据
        String wholeBody = getWholeBody();
        String orgDesInfo= StringUtils.substringBetween(wholeBody, "<body>", "</body>");

        // 加密
        // 加密调试——2天
        // ①加密算法实现不同
        // ②加密的原始数据不同

        DES des=new DES();
        return des.authcode(orgDesInfo, "DECODE", ConstantValue.DES_PASSWORD);
    }
}
