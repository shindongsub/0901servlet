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
  <a href="http://localhost:8090/article?cmd=read&id= <%= articles.get(i).getId() %>"> 제목    : <%= articles.get(i).getTitle() %></a><br>
  내용    : <%= articles.get(i).getBody() %><br>
  작성자 : <%= articles.get(i).getNickname() %><br>
  조회수 : <%= articles.get(i).getHit() %><br>
  <hr>

</div>

<%	
}
%>
<a href="http://localhost:8090/article?cmd=showArticle">글쓰기</a>
</body>
</html>