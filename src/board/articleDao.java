package board;

import java.sql.Connection;
import java.sql.DriverManager;
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
			
			String sql = "INSERT INTO `like` " + 
					"SET aid = " + aid + "," + 
					"uid = '" + uid + "'," + 
					"likeFlag = '" + flag + "', " + 
					"regDate = NOW()";

			stmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close();
		}	
		
	}
}