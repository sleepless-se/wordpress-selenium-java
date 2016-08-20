package com.wordpress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp ;

/**
 * Hello world!
 *
 */
public class WpQuery {
	private DbManager db = new DbManager();
	private Date dt;
	private Calendar cal;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");

	public static void main(String[] args) {
		WpQuery app = new WpQuery();
		Post post = new Post();
		post.setBody("body");
		post.setTitle("tilte");
		app.getWpDbManager().setConnection();
		System.out.println(app.lastPostTime());
		// System.out.println(app.post(post));
		app.getWpDbManager().close();

		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DbManager getWpDbManager() {
		return this.db;
	}

	public int post(Post post) {
		int num = 0;
		try {

			String sql = "insert into wp_4tuvc6_posts (post_author, post_title,post_content,post_status,post_excerpt,to_ping,pinged,post_content_filtered,post_date,post_name,post_date_gmt) values (?, ?, ?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = getWpDbManager().getConnection().prepareStatement(sql);
			pstmt.setString(1, "1");
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getBody());
			pstmt.setString(4, "future");
			pstmt.setString(5, "");
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setString(8, "");
			pstmt.setTimestamp(9, post.getPostDate());
			pstmt.setString(10, post.getTitle());
			pstmt.setTimestamp(11, post.getPostDate());
			num = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		}
		return num;
	}

	public int checkMovie(String id) {
		int con = 0;
		try {
			String sql = "select count(*) as con from wp_4tuvc6_posts where post_content like '%" + id + "%'";
			System.out.println(sql);
			PreparedStatement pstmt = getWpDbManager().getConnection().prepareStatement(sql);
			ResultSet result = pstmt.executeQuery(sql);
			while (result.next()) {
				con = result.getInt("con");
			}
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		}
		return con;

	}
	public Timestamp lastPostTime() {
		Timestamp time = null;
		try {
//			String sql = "select UNIX_TIMESTAMP(post_date + interval 15 minute) AS TIMESTAMP  from wp_4tuvc6_posts order by post_date desc limit 1";
			String sql = "select post_date + interval 14 minute AS TIMESTAMP  from wp_4tuvc6_posts order by post_date desc limit 1";
			System.out.println(sql);
			PreparedStatement pstmt = getWpDbManager().getConnection().prepareStatement(sql);
			ResultSet result = pstmt.executeQuery(sql);
			while (result.next()) {
				time = result.getTimestamp("TIMESTAMP");
			}
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
			e.printStackTrace();
		}
		return time;

	}

	public int addMovie(String url) {
		int num = 0;
		try {
			String sql = "insert into movies (url) values (?)";
			PreparedStatement pstmt = getWpDbManager().getConnection().prepareStatement(sql);
			pstmt.setString(1, url);
			num = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		}
		return num;

	}

}
