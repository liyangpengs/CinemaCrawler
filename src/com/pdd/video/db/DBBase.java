package com.pdd.video.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DBBase {
	private String driverClass="com.mysql.jdbc.Driver";
	private  String url="jdbc:mysql://yzy162461.mysql.rds.aliyuncs.com:3306/cinema?useUnicode=true&characterEncoding=UTF-8";
	private  String userName="root";
	private  String usePwd="Yzy162461";
	private Connection conn=null;
	private PreparedStatement ps=null;
	
	private Connection getConn(){
		try {
			Class.forName(driverClass);
			conn=DriverManager.getConnection(url, userName, usePwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public int ExecuteUpdate(String sql,Object [] obj) throws Exception{
		this.getConn();
		int status=0;
		ps=conn.prepareStatement(sql);
		if(null!=obj&&obj.length>0){
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i+1, obj[i]);
			}
		}
		status=ps.executeUpdate();
		ps.close();
		conn.close();
		return status;
	}
	
	public ResultSet ExecuteQuery(String sql,Object [] obj){
		this.getConn();
		ResultSet set=null;
		try {
			ps=conn.prepareStatement(sql);
			if(null!=obj&&obj.length>0){
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i+1, obj[i]);
				}
			}
			set=ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	
	public ResultSet ExecuteProcedure(String sql,Object [] obj){
		this.getConn();
		ResultSet set=null;
		try {
			CallableStatement cs=conn.prepareCall(sql);
			if(null!=obj&&obj.length>0){
				for (int i = 0; i < obj.length; i++) {
					cs.setObject(i+1, obj[i]);
				}
			}
			set=cs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				set.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return set;
	}
	/**
	 * 新增数据后返回id
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int InsertVideo(String sql,Object [] obj) throws Exception{
		this.getConn();
		ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		if(null!=obj&&obj.length>0){
			for (int i = 0; i < obj.length; i++) {
				ps.setObject(i+1, obj[i]);
			}
		}
		ps.executeUpdate();
		ResultSet result=ps.getGeneratedKeys();
		if (result.next()) {
			return result.getInt(1);
        }
		result.close();
		ps.close();
		conn.close();
		return 0;
	}
}
