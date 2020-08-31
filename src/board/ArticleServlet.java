package board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {

	articleDao dao = new articleDao();
	
	final String ARTICLE_PATH = "WEB-INF/article/";
	final String EXTENTION = ".jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");

		String cmd = request.getParameter("cmd");
		PrintWriter pw = response.getWriter(); // 브라우저에 찍어라
		if (cmd.equals("list")) {
					
			List<Article> articles = dao.getAllArticles();
			//request객체에 데이터 저장
			request.setAttribute("articles", articles);
			//forwarding 해야되는데, 해주는 녀석이 RequestDispatcher
//			RequestDispatcher dis = request.getRequestDispatcher(ARTICLE_PATH+"list"+EXTENTION); 밑에껄로 변환되는과정임. 메서드 forwarding
			String url = ARTICLE_PATH+"list"+EXTENTION;
			forwarding(request, response, url);

		} else if (cmd.equals("read")) {
			String id = request.getParameter("id");
			Article article = dao.getArticleById(id);
			List<Reply> replies = dao.getRepliesByArticleId(id);
			
			request.setAttribute("article", article);
			request.setAttribute("replies", replies);
			
			String url = ARTICLE_PATH+"detail"+EXTENTION;
			forwarding(request, response, url);
		} else if (cmd.equals("detailForm")) {
			String id = request.getParameter("id");
			Article article = dao.getArticleById(id);
			request.setAttribute("article", article);
			String url = ARTICLE_PATH+"detailForm"+EXTENTION;
			forwarding(request, response, url);
			
		}else if(cmd.equals("update")) {
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			dao.updateArticle(id, title, body);
			
			response.sendRedirect("article?cmd=read&id="+id);  //설정해놓은 주소로 보내는 명령어 sendRedirect
			
			
		} else if(cmd.equals("delete")) {
			String id = request.getParameter("id");
			
			dao.deleteArticle(id);
		} else if(cmd.equals("reply")) {
			String body = request.getParameter("body");
			String parentId = request.getParameter("parentId");
			
			dao.addReply(body, parentId);
			
			// 댓글번호, 부모글번호, 내용, 작성자, 작성일 
			
		}else if(cmd.equals("search")) {
			String keyword = request.getParameter("keyword");
			List<Article> list = dao.getArticlesBykeyword(keyword);
			request.setAttribute("articles", list);
			String url = ARTICLE_PATH+"list"+EXTENTION;
			forwarding(request, response, url);
			
			
		} else if(cmd.equals("like")) {
			String aid = (String)request.getParameter("aid");
			String uid = (String)request.getParameter("uid");
			String flag = (String)request.getParameter("flag");
			int likeFlag = 0;
			if(flag.equals("like")) {
				likeFlag = 1;
			}else {
				likeFlag = 2;
			}
			dao.addLike(aid, uid, likeFlag);
			
		} else if(cmd.equals("articleForm")) {
			pw.println("<form action='http://localhost:8090/article'>");
			pw.println("<input type='text' name='cmd' placeholder = '명령어 입력'>");
			pw.println("<input type='text' name='id' placeholder = '번호'>");
			pw.println("<input type='submit'>");
			pw.println("</form>");
		}
	}
	void forwarding(HttpServletRequest req, HttpServletResponse res, String url) {
		RequestDispatcher rd = req.getRequestDispatcher(url);
		try {
			rd.forward(req, res);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}