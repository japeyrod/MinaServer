package com.xy.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xy.common.Tools;
import com.xy.db.DBManager;
import com.xy.db.jdbc.ConnectionPoolManager;
import com.xy.game.message.MessageManager;
import com.xy.net.mina.service.MinaNetworkManager;

/**
 * ��Ϸ���̣߳��������������
 */
public class StartGame {
	public static void main(String[] args) throws Exception{
		
		/**Ӳ�����ݿ��ʼ��������Ϣ*/
		ConnectionPoolManager.JDBCInit();
		
		/**��Ϣ��Ϣ��ʼ��*/
		MessageManager.init();
		
		/**�ڴ����ݿ��ʼ��*/
		System.out.println("�ȴ��ڴ����ݿ��ʼ��...");
		DBManager.dbInit();
		System.out.println("ok������");
		
		/**TCP�����ӷ��񣬼���Ϸ������������½��������*/
		MinaNetworkManager network = new MinaNetworkManager();
		network.startNetwork();

		//System.out.println(test("���", "���"));
	}

	public static int test(String playerName, String password){
		try {
			Connection conn = ConnectionPoolManager.getConnection();
			String sql = "select * from players where playerName = ?";
			PreparedStatement preStat = conn.prepareStatement(sql);
			Tools.setPreStatementItems(preStat, playerName);
			ResultSet rs = preStat.executeQuery();
			if(rs.next()){
				rs.close();
				preStat.close();
				ConnectionPoolManager.pushBackConnection(conn);
				return 1;
			}
			sql = new String("insert into players(playerName, password) values(?,?)");
			preStat = conn.prepareStatement(sql);
			Tools.setPreStatementItems(preStat, playerName, password);
			if(preStat.executeUpdate() != 0){
				preStat.close();
				ConnectionPoolManager.pushBackConnection(conn);
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 2;
	}
}
