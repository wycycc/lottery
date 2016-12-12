package com.ycc.lottery.net.protocal.element;

import com.ycc.lottery.net.protocal.Element;
import com.ycc.lottery.net.protocal.Leaf;

import org.xmlpull.v1.XmlSerializer;

/**
 * 获取当前销售期的请求
 * Created by Administrator on 2016/12/8.
 */
public class CurrentIssueElement extends Element {
    // <lotteryid>118</lotteryid>
    private Leaf lotteryid = new Leaf("lotteryid");
    // <issues>1</issues>
    private Leaf issues = new Leaf("issues", "1");

    /******************服务器回复**********************/
    private String issue;
    private String lasttime;


    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    /*********************************************/

    @Override
    public void serializerElement(XmlSerializer serializer) {
        try {
            serializer.startTag(null, "element");
            lotteryid.serializerLeaf(serializer);
            issues.serializerLeaf(serializer);
            serializer.endTag(null, "element");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTransactionType() {
        return "12002";
    }

    public Leaf getLotteryid() {
        return lotteryid;
    }



}
