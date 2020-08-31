<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="board.Article" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
List<Article> articles = (List<Article>)request.getAttribute("articles");
%>
<h1> 게시물 목록 </h1>
<%
for(int i=0; i < articles.size(); i++){
%>
<div>
  번호    : <%= articles.get(i).getId() %><br>
  제목    : <%= articles.get(i).getTitle() %><br>
  내용    : <%= articles.get(i).getBody() %><br>
  작성자 : <%= articles.get(i).getNickname() %><br>
  조회수 : <%= articles.get(i).getHit() %><br>
  <hr>

</div>

<%	
}
%>

</body>
</html>