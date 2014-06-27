package com.xy.game.message.handler;


import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;

import com.xy.common.struct.constants.MessageType;

/**
 * ��ɫ�����Ϣ������
 */
public class RoleHandler extends AbstractHandler{
	
	public RoleHandler(){}
	
	public RoleHandler(IoSession session, JSONObject message){
		super(session,message);
	}
	
	@Override
	public void handle() {
		switch(message.getInt("msgType")){
		case MessageType.ROLE_GET:
			getRoleInfo();
			break;
		default:
			break;
		}
	}
	
	/**
	 * ��ȡ��ɫȫ����Ϣ
	 */
	public void getRoleInfo(){
		JSONObject sendJson = new JSONObject();
		//sendJson = new RoleDAO().getRoleInformation();
		session.write(sendJson);
		return;
	}
}


