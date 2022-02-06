<%@page import="com.db.ConnectDB"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ConnectDB connectDB = ConnectDB.getInstance();

	String id = request.getParameter("user_id");
	String pw = request.getParameter("user_pw");
	String returns = connectDB.LoginDB(id, pw);
	
	System.out.println(returns);
	// 안드로이드로 전송
	out.println(returns);
%>
