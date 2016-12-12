package com.ycc.lottery.engine.user;

import android.util.Xml;

import com.ycc.lottery.ConstantValue;
import com.ycc.lottery.bean.ShoppingCart;
import com.ycc.lottery.bean.Ticket;
import com.ycc.lottery.bean.User;
import com.ycc.lottery.engine.BaseEngine;
import com.ycc.lottery.engine.UserEngine;
import com.ycc.lottery.net.HttpClientUtil;
import com.ycc.lottery.net.protocal.Message;
import com.ycc.lottery.net.protocal.element.BalanceElement;
import com.ycc.lottery.net.protocal.element.BetElement;
import com.ycc.lottery.net.protocal.element.UserLoginElement;
import com.ycc.lottery.utils.DES;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;


public class UserEngineImpl extends BaseEngine implements UserEngine {
	/**
	 * 用户登录
	 * 
	 * @param user
	 */
	public Message login(User user) {
		// 第一步：获取到登录用的xml
		// 创建登录用Element
		UserLoginElement element = new UserLoginElement();
		// 设置用户数据
		element.getActpassword().setTagValue(user.getPassword());
		// Message.getXml(element)
		Message message = new Message();
		message.getHeader().getUsername().setTagValue(user.getUsername());
		String xml = message.getXml(element);

		// 如果第三步比对通过result，否则返回空
		Message result = getResult(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement().setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement().setErrormsg(parser.nextText());

						}
						break;
					}
					eventType = parser.next();
				}

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;

	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 */
	public Message login1(User user) {
		// 第一步：获取到登录用的xml
		// 创建登录用Element
		UserLoginElement element = new UserLoginElement();
		// 设置用户数据
		element.getActpassword().setTagValue(user.getPassword());
		// Message.getXml(element)
		Message message = new Message();
		message.getHeader().getUsername().setTagValue(user.getUsername());
		String xml = message.getXml(element);

		// 第二步(代码不变)：发送xml到服务器端，等待回复
		// HttpClientUtil.sendXml
		// 在这行代码前，没有判断网络类型？
		HttpClientUtil util = new HttpClientUtil();
		InputStream is = util.sendXml(ConstantValue.LOTTERY_URI, xml);
		// 判断输入流非空
		if (is != null) {
			Message result = new Message();

			// 第三步(代码不变)：数据的校验（MD5数据校验）
			// timestamp+digest+body
			XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(is, ConstantValue.ENCONDING);

				int eventType = parser.getEventType();
				String name;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("timestamp".equals(name)) {
							result.getHeader().getTimestamp().setTagValue(parser.nextText());
						}
						if ("digest".equals(name)) {
							result.getHeader().getDigest().setTagValue(parser.nextText());
						}
						if ("body".equals(name)) {
							result.getBody().setServiceBodyInsideDESInfo(parser.nextText());
						}
						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// 原始数据还原：时间戳（解析）+密码（常量）+body明文（解析+解密DES）
			// body明文（解析+解密DES）
			DES des = new DES();
			String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

			String orgInfo = result.getHeader().getTimestamp().getTagValue() + ConstantValue.AGENTER_PASSWORD + body;

			// 利用工具生成手机端的MD5
			String md5Hex = DigestUtils.md5Hex(orgInfo);
			// 将手机端与服务器的进行比对
			if (md5Hex.equals(result.getHeader().getDigest().getTagValue())) {
				// 第四步：请求结果的数据处理
				// body部分的第二次解析，解析的是明文内容

				parser = Xml.newPullParser();
				try {
					parser.setInput(new StringReader(body));

					int eventType = parser.getEventType();
					String name;

					while (eventType != XmlPullParser.END_DOCUMENT) {
						switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equals(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equals(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());

							}
							break;
						}
						eventType = parser.next();
					}

					return result;

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		return null;
	}

	@Override
	public Message getBalance(User user) {
		BalanceElement element = new BalanceElement();

		Message message = new Message();
		message.getHeader().getUsername().setTagValue(user.getUsername());
		String xml = message.getXml(element);

		Message result = super.getResult(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				BalanceElement resultElement = null;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement().setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement().setErrormsg(parser.nextText());
						}

						// 正对于当前请求
						if ("element".equals(name)) {
							resultElement = new BalanceElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("investvalues".equals(name)) {
							if (resultElement != null) {
								resultElement.setInvestvalues(parser.nextText());
							}
						}

						break;
					}
					eventType = parser.next();
				}

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	@Override
	public Message bet(User user) {
		BetElement element = new BetElement();
		element.getLotteryid().setTagValue(ShoppingCart.getInstance().getLotteryid().toString());

		// 彩票的业务里面：
		// ①关于注数的计算
		// ②关于投注信息封装（用户投注号码）

		// 010203040506|01^01020304050607|01

		StringBuffer codeBuffer = new StringBuffer();
		for (Ticket item : ShoppingCart.getInstance().getTickets()) {
			codeBuffer.append("^").append(item.getRedNum().replaceAll(" ", "")).append("|").append(item.getBlueNum().replaceAll(" ", ""));
		}

		element.getLotterycode().setTagValue(codeBuffer.substring(1));

		element.getIssue().setTagValue(ShoppingCart.getInstance().getIssue());
		element.getLotteryvalue().setTagValue((ShoppingCart.getInstance().getLotteryvalue() * 100) + "");

		element.getLotterynumber().setTagValue(ShoppingCart.getInstance().getLotterynumber().toString());
		element.getAppnumbers().setTagValue(ShoppingCart.getInstance().getAppnumbers().toString());
		element.getIssuesnumbers().setTagValue(ShoppingCart.getInstance().getIssuesnumbers().toString());

		element.getIssueflag().setTagValue(ShoppingCart.getInstance().getIssuesnumbers() > 1 ? "1" : "0");

		Message message = new Message();
		message.getHeader().getUsername().setTagValue(user.getUsername());

		String xml = message.getXml(element);

		Message result = super.getResult(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				BetElement resultElement = null;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement().setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement().setErrormsg(parser.nextText());
						}

						// 正对于当前请求
						if ("element".equals(name)) {
							resultElement = new BetElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("actvalue".equals(name)) {
							if (resultElement != null) {
								resultElement.setActvalue(parser.nextText());
							}
						}

						break;
					}
					eventType = parser.next();
				}

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}
}
