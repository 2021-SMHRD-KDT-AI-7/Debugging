package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.net.aso.e;

public class LoginDB {

	// 2차프로젝트_팀 계정 conn
	String jdbcUrl = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
	String userId = "debugging";
	String userPw = "smhrd123";

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql;
	MemberDTO member;

	public void getConn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbcUrl, userId, userPw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MemberDTO Login(String id, String pwd) {
		getConn();
		String sql = "select * from tb_user where user_id=? and user_pw=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if (rs.next()) {
				String user_id = rs.getString("user_id");
				String user_pw = rs.getString("user_pw");
				String user_name = rs.getString("user_name");
				String user_bd = rs.getString("user_bd");
				member = new MemberDTO(user_id, user_pw, user_name, user_bd);
				System.out.println(member);
				System.out.println("로그인 성공");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		return member;
	}

	public void Join(MemberDTO memberDTO) {
		getConn();
		String sql = "INSERT INTO t_member VALUES(?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, memberDTO.getUser_id());
			ps.setString(2, memberDTO.getUser_pw());
			ps.setString(3, memberDTO.getUser_name());
			ps.setString(4, memberDTO.getUser_bd());
			ps.executeUpdate();

			System.out.println(memberDTO);
			System.out.println("회원가입 성공");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
	}
}
