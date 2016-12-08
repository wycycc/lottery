package com.ycc.lottery.net.protocal.element;

import com.ycc.lottery.net.protocal.Element;
import com.ycc.lottery.net.protocal.Leaf;

import org.xmlpull.v1.XmlSerializer;

/**
 * 用户登录请求
 * Created by Administrator on 2016/12/8.
 */
public class UserLoginElement extends Element {
    private Leaf actpassword = new Leaf("actpassword");

    @Override
    public void serializerElement(XmlSerializer serializer) {
        try {
            serializer.startTag(null, "element");
            actpassword.serializerLeaf(serializer);
            serializer.endTag(null, "element");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTransactionType() {
        return "14001";
    }

    public Leaf getActpassword() {
        return actpassword;
    }

}
