package com.ycc.lottery.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 工厂类
 * @author Administrator
 *
 */
public class BeanFactory {
	// 依据配置文件加载实例
	
	private static Properties properties;
	static{
		properties=new Properties();
		// bean.properties必须在src的跟目录下
		try {
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 加载需要的实现类
	 * @param clazz
	 * @return
	 */
	public static<T> T getImpl(Class<T> clazz)
	{
		String key=clazz.getSimpleName();//clazz.getName()
		String className = properties.getProperty(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
