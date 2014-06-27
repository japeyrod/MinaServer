package com.xy.game.message;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.xy.common.Tools;
import com.xy.game.message.handler.AbstractHandler;

public class MessageManager {
	
	/**��Ϣ���������ļ�*/
	private static Tools properties;
	
	/**�Ự*/
	private IoSession session;		
	
	/**��Ϣ*/
	private JSONObject message;	
	
	/**session����*/
	public static HashMap<Long, IoSession> sessionSet = new HashMap<Long, IoSession>();
	
	/**�ѳɹ���½�����session����*/
	public  static HashMap<String, IoSession> playerSet = new HashMap<String, IoSession>();
	
	public MessageManager(IoSession session, Object message){
		this.session = session;
		this.message = JSONObject.fromObject(message.toString());
	}

	
	public static void init(){
		properties = new Tools();
		properties.getProperties("resource" + java.io.File.separator + "messageType.ini");
		return;
	}
	
	/**
	 * ������Ϣ��������Ϣ���͵����Ӧ����Ϣ�����ߴ���
	 */
	public void msgTransfer() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//String s = properties.getStringFromProperty(message.getInt("msgType") + "");
		Class<?> transmission = Class.forName(properties.getStringFromProperty(message.getInt("msgType") + ""));
		Constructor<?> test = transmission.getConstructor(IoSession.class, JSONObject.class);
		AbstractHandler handler = (AbstractHandler) test.newInstance(session, message);
		handler.handle();
	}
}
