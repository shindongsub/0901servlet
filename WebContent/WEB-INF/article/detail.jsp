<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.Article" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="board.Reply" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Article article = (Article)request.getAttribute("article");
ArrayList<Reply> replies = (ArrayList<Reply>)request.getAttribute("replies");
%>
<div>
번호    : <%= article.getId() %> <br>
제목    : <%= article.getTitle() %> <br>
내용    : <%= article.getBody() %> <br>
작성자 : <%= article.getNickname() %> <br>
조회수 : <%= article.getHit() %> <br>
<hr>
</div>
<div>
<%
for(Reply reply : replies){ %>
	내용    : <%= replies.get(0).getBody() %> <br>
	작성자 : <%= replies.get(0).getWriter() %> <br>
	작성일 : <%= replies.get(0).getRegDate() %> <br>
	<hr>
<%}%>
</div>
<a href="article?cmd=detailForm&id=<%= article.getId() %>"> 수정 </a>

</body>
</html>