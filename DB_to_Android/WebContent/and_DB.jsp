<%@page import="com.db.ConnectDB"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
   String id = request.getParameter("user_id");
   String pw = request.getParameter("user_pw");
   String name = request.getParameter("user_name");
   String bd = request.getParameter("user_bd");

	
   String returns = ConnectDB.connectionDB(id, pw, name, bd);

   System.out.println(returns);

   // 안드로이드로 전송
   out.println(returns);
%>
