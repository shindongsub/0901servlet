<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.Article" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Article article = (Article)request.getAttribute("article");
%>
<div>
<form action="article">
번호    : <%= article.getId() %> <br>
제목    : <input type="text" name="title" value="<%= article.getTitle()%>"> <br>
내용    : <input type="text" name="body" value="<%= article.getBody()%>"> <br>
작성자 : <%= article.getNickname() %> <br>
조회수 : <%= article.getHit() %> <br>
<input type = "hidden" name="cmd" value = "update">
<input type = "hidden" name="id" value = "<%= article.getId() %>">
<input type = "submit">
</form>
<hr>
</div>
</body>
</html>