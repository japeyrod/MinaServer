package com.xy.net.mina.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xy.game.message.MessageManager;

/**
 * mina��ܵĽ�����Ϣ�࣬�̳���IoHandlerAdapter����IoHandler
 */
public class MinaSessionHandler extends IoHandlerAdapter{
	
	private int number = 0;
	/**��־��Ϣ*/
	private final static Logger log = LoggerFactory.getLogger(MinaSessionHandler.class);
	
	
	/**
	 * ������Ϣ��������Ϣ���͸���Ϣ������
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		log.info("REQUEST_NUMBER: " + (++number));
		new MessageManager(session, message).msgTransfer();
		/*byte send = 1;
		System.out.println(send);
		session.write(send);*/
	}
	
	/**
	 * ����Ϣ������֮����ã������ڼ��㷢����Ϣ������
	 */
	@Override
	public void messageSent(IoSession session, Object message){
		log.info("MESSAGE SENT: " + message.toString());
		log.info("SESSION��ROLEID: " + session.getAttribute("roleId"));
		log.info("SESSION��ROLENAME: " + session.getAttribute("roleName"));
	}
	
	/**
	 * �Ự��session�����󱻴�����ʱ�����
	 */
	@Override
	public void sessionCreated(IoSession session){
		MessageManager.sessionSet.put(session.getId(),session);
	}
	
	/**
	 * ���ӱ��򿪵�ʱ�����
	 */
	@Override
	public void sessionOpened(IoSession session){
	}
	
	/**
	 *�Ự��session���رյ�ʱ����� 
	 */
	@Override
	public void sessionClosed(IoSession session){
		MessageManager.playerSet.remove((String)session.getAttribute("roleName"));
		MessageManager.sessionSet.remove(session.getId());
	}
	
	/**
	 * �Ựͨ�����ڿ��е�ʱ�����
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status){
		log.info("session will be closed");
		session.close();
		MessageManager.sessionSet.remove(session.getId());
		//playerSet.remove(session.getRemoteAddress().toString());
		//	System.out.println( "session is free, IDLE " + session.getIdleCount(status));
	}
	
	/**
	 * �����쳣ʱ����һ�������رջỰ
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause){
		MessageManager.sessionSet.remove(session.getId());
		/*session.write("session exception");
		session.write("session will been closed");
		session.close();*/
	}
}
