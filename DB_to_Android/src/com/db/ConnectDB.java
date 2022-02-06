package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
	
	// 2차프로젝트_팀 계정 conn
	String jdbcUrl = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
	String userId = "debugging";
	String userPw = "smhrd123";

	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;

	ResultSet rs = null;

	String sql = "";
	String sql2 = "";
	String returns = "실패";

	public String connectionDB(String user_id, String user_pw, String user_name, String user_bd) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbcUrl, userId, userPw);

			sql = "SELECT U_ID FROM TB_USER WHERE U_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				returns = "이미 존재하는 아이디 입니다.";
			} else {
				sql2 = "INSERT INTO TB_USER VALUES(?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, user_id);
				pstmt2.setString(2, user_pw);
				pstmt2.setString(3, user_name);
				pstmt2.setString(4, user_bd);
				pstmt2.executeUpdate();
				returns = "회원 가입 성공 !";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

}
