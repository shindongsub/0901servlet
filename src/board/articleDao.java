package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class articleDao {

	String url = "jdbc:mysql://localhost:3306/t1?serverTimezone=UTC";
	String id = "root";
	String pw = "";

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	Connection getConnection() {

		Connection conn = null;

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	void insertArticle(String title, String body) {

		try {

			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "insert into article set title = '" + title + "', body = '" + body
					+ "', nickname = '유저99', regDate = '20200817'";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close();
		}

	}

	List<Article> getAllArticles() {

		List<Article> articles = null;

		try {
			String sql = "select * from article";

			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			articles = new ArrayList<>();

			while (rs.next()) {

				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				String nickname = rs.getString("nickname");
				int hit = rs.getInt("hit");

				Article article = new Article(id, title, body, nickname, hit);

				articles.add(article);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return articles;
	}

	public Article getArticleById(String id) {
		String sql = "select * from article where id = " + id;
		Article article = null;
		conn = getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				article = new Article();

				article.setId(rs.getInt("id"));
				article.setTitle(rs.getString("title"));
				article.setBody(rs.getString("body"));
				article.setNickname(rs.getString("nickname"));
				article.setHit(rs.getInt("hit"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return article;
	}

	public void updateArticle(String id, String title, String body) {
		String sql = "update article set title = '" + title + "', body = '" + body + "' where id = " + id;
		System.out.println(sql);
		conn = getConnection();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteArticle(String id) {
		String sql = "delete from article where id =" + id;
		String sq2 = "delete from reply where parentId =" + id;
		String sq3 = "delete from `like` where aid =" + id;

		conn = getConnection();
		try {
			stmt = conn.createStatement();
			//자동커밋 해제
			conn.setAutoCommit(false);
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sq2);
			stmt.executeUpdate(sq3);
			//커밋 불러와주고.
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				//롤백해주고 try문해주면 끝! 하는이유? id값이 3개중에 2개다. 하난없다? 그럼 삭제안되. 나머지 두개를 삭제하는방법임.
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			close();
		}
	}

	void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addReply(String body, String parentId) {
		try {

			conn = getConnection();
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO reply " + 
					"SET parentId = " + parentId + "," + 
					"`body` = '" + body + "'," + 
					"writer = '익명'," + 
					"regDate = NOW()";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close();
		}	
	}

	public List<Reply> getRepliesByArticleId(String id) {
		
		List<Reply> replies = null;

		try {
			String sql = "select * from reply where parentId = " + id;

			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			replies = new ArrayList<>();

			while (rs.next()) {

				int rid = rs.getInt("id");
				int parentId = rs.getInt("parentId");
				String body = rs.getString("body");
				String writer = rs.getString("writer");
				String regDate = rs.getString("regDate");

				Reply reply = new Reply(rid, parentId, body, writer, regDate);

				replies.add(reply);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return replies;
	}

	public List<Article> getArticlesBykeyword(String keyword) {
		List<Article> articles = null;

		try {
			String sql = "select * from article where title like '%"+keyword+"%'";

			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			articles = new ArrayList<>();

			while (rs.next()) {

				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				String nickname = rs.getString("nickname");
				int hit = rs.getInt("hit");

				Article article = new Article(id, title, body, nickname, hit);

				articles.add(article);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return articles;
	}

	public void addLike(String aid, String uid, int flag) {
		try {

			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "INSERT INTO `like` set aid = "+aid+", uid = "+uid+", likeFlag = "+flag+", regDate = now()" ;

			stmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close();
		}	
		
	}

	public int checkLikeDuplication(String aid, String uid) {
		String sql = "select count(*) cnt from `like` where aid = " + aid+" and uid = "+uid;
		int rst = 0;
		conn = getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				rst = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rst;
	}

	public int getLikeByArticleIdAndUserId(String aid, String uid) {
		String sql = "select likeFlag from `like` where aid = " + aid+" and uid = "+uid;
		int rst = 0;
		conn = getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				rst = rs.getInt("likeFlag");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rst;
	}

	public void deleteLikeByArticleIdAndUserId(String aid, String uid) {
		String sql = "delete from `like` where aid =" + aid + " and uid = "+uid;
		conn = getConnection();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void updateLikeByArticleIdAndUserId(String aid, String uid, int likeFlag) {
		String sql = "update `like` set likeFlag = " + likeFlag +" where aid = " + aid + " and uid = "+uid;
		System.out.println(sql);
		conn = getConnection();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

	public Member loginChek(String id, String pw) {
		String sql = "select * from login where loginId = ? and loginPw = ?";
		Member member = null;
		conn = getConnection();
		try {
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
//			stmt = conn.createStatement();
			rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new Member();

				member.setId(rs.getInt("id"));
				member.setLoginId(rs.getString("loginId"));
				member.setLoginPw(rs.getString("loginPw"));
				member.setNickname(rs.getString("nickname"));
				member.setRegDate(rs.getString("RegDate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
		
	}

	public List<Article> getByCurrentPage(String currentPage) {
		List<Article> articles = null;
		int currentPageNo = Integer.parseInt(currentPage);
		int start = (currentPageNo - 1)*3;
		
		
		try {
			String sql = "select * from article limit "+start+", 3";
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			articles = new ArrayList<>();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				String nickname = rs.getString("nickname");
				int hit = rs.getInt("hit");
				
				Article article = new Article(id, title, body, nickname, hit);
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}



	
	
}