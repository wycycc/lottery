package com.ycc.lottery.net.protocal;

import org.xmlpull.v1.XmlSerializer;

/**
 * 请求数据的封装
 * Created by Administrator on 2016/12/8.
 */
public abstract class Element {
    // 不会将所有的请求用到的叶子放到Element中
    // Element将作为所有请求的代表，Element所有请求的公共部分
    // 公共部分：
    // ①每个请求都需要序列化自己

    /**
     * 每个请求都需要序列化自己
     *
     * @param serializer
     */
    public abstract void serializerElement(XmlSerializer serializer);
    // ②每个请求都有自己的标示

    /**
     * 每个请求都有自己的标示
     *
     * @return
     */
    public abstract String getTransactionType();


    // 包含内容
    // 序列化
    // 特有：请求标示

    // <lotteryid>118</lotteryid>
    //	private Leaf lotteryid = new Leaf("lotteryid");
        // <issues>1</issues>
    //	private Leaf issues = new Leaf("issues", "1");


    //	public Leaf getLotteryid() {
    //		return lotteryid;
    //	}

    //	/**
    //	 * 序列化请求
    //	 */
    //	public void serializerElement(XmlSerializer serializer) {
    //		try {
    //			serializer.startTag(null, "element");
    //			lotteryid.serializerLeaf(serializer);
    //			issues.serializerLeaf(serializer);
    //			serializer.endTag(null, "element");
    //		} catch (Exception e) {
    //			e.printStackTrace();
    //		}
    //	}

    //	/**
    //	 * 获取请求的标示
    //	 */
    //	public String getTransactionType() {
    //		return "12002";
    //	}
}
