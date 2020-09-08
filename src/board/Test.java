package board;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class Test {

	public static void main(String[] args) {
		BasicDataSource dbs = new BasicDataSource(); // connection pool
		dbs.setDriverClassName("com.mysql.cj.jdbc.Driver"); //driver 설정
		//url설정
		dbs.setUrl("jdbc:mysql://localhost:3306/t1?serverTimezone=UTC");
		//아이디
		dbs.setUsername("root");
		//비밀번호
		dbs.setPassword("");
		
		try {
			Connection conn = dbs.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from article");
			rs.next();
			System.out.println(rs.getString("title"));

			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
