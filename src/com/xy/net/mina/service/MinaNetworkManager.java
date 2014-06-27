package com.xy.net.mina.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;


//import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.xy.net.mina.protocol.MinaCodecFactory;

/**
 * mina��ܵ�������(TCP������)������Ϸ������IoService
 */
public class MinaNetworkManager implements Runnable{
	/**
	 *�������ӹ��������췽��������TCP���������߳� 
	 */
	public MinaNetworkManager(){
		new Thread(this).start();
	}
	
	/**
	 * ��������service
	 */
	public void startNetwork() throws Exception{
		//IoAcceptor acceptor = new NioSocketAcceptor(1);//����processor����Ϊ5��cpu����+1��
		NioSocketAcceptor acceptor = new NioSocketAcceptor(1);
		
		acceptor.getFilterChain().addLast("Logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(
				new MinaCodecFactory(Charset.forName( "UTF-8" ))));
		acceptor.setHandler(new MinaSessionHandler());
		acceptor.getSessionConfig().setReadBufferSize( 2048 );  
		acceptor.getSessionConfig().setReceiveBufferSize(2048);
		acceptor.getSessionConfig().setSendBufferSize(4096);
		acceptor.getSessionConfig().setTcpNoDelay(true);
		//acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 60 ); 
		try {
			acceptor.bind(new InetSocketAddress(9888));  
		} catch (IOException e) {  
			e.printStackTrace();  
		}
	}
	
	/**
	 * �ر�����
	 */
	public void stopNetwork(){
		return ;
	}
	
	/**
	 * �����������ã����綨ʱ�����
	 */
	public void run(){
		
	}
}
