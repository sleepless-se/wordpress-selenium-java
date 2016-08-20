package com.wordpress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DbManager {
	protected DbManager dbManager = null;
	protected String driverName = "com.mysql.jdbc.Driver";
	protected String dbName = null;
	protected String userName = null;
	protected String password = null;
	protected String hostName = null;
	protected String port = null;
	protected int count = 0;
	protected PreparedStatement ps = null;
	protected Connection connection = null;
	protected ArrayList<String> selectList = new ArrayList();
	protected ArrayList<String> selectWhereList = new ArrayList();
	protected ArrayList<String> removedList = new ArrayList();
	protected ResultSet select = null;

	public void close() {
		try {
			connection.close();
			System.out.println("Closed connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getSelect() {
		return this.select;
	}

	public int getCount() {
		return count;
	}

	public void setSelect(String sql) {
		this.select = null;
		System.out.println(sql);
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Timestamp getTodayTimestamp() {
		java.util.Date date = null;
		java.sql.Timestamp timeStamp = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			java.sql.Date dt = new java.sql.Date(calendar.getTimeInMillis());
			java.sql.Time sqlTime = new java.sql.Time(calendar.getTime()
					.getTime());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			date = simpleDateFormat.parse(dt.toString() + " "
					+ sqlTime.toString());
			timeStamp = new java.sql.Timestamp(date.getTime());
		} catch (ParseException pe) {
		} catch (Exception e) {
		}
		return timeStamp;
	}

	public void setCount(String table, String column, String value) {
		String sql = "SELECT COUNT(*) count FROM " + table + " WHERE " + column
				+ " = '" + value + "'";
		try {
			this.connection.setAutoCommit(false);
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery(sql);
			try {
				while (resultSet.next()) {
					this.count = resultSet.getInt("count");
					// System.out.println(userId);
				}
			} catch (SQLException q) {
				connection.commit();
				ps.close();
				connection.close();
				System.out.println("setCount()" + q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void setCount(String sql) {
		System.out.println(sql);
		try {
			this.connection.setAutoCommit(false);
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery(sql);
			try {
				while (resultSet.next()) {
					this.count = resultSet.getInt("count");
					// System.out.println(userId);
				}
			} catch (SQLException q) {
				connection.commit();
				ps.close();
				connection.close();
				System.out.println("setCount()" + q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Connection getConnection() {
		if (connection == null) {
			System.out.println("please setConnection()");
		}
		return this.connection;
	}

	public void setConnection() {
		String jdbcUrl = "jdbc:mysql://" + hostName + ":" + port + "/" + dbName
				+ "?user=" + userName + "&password=" + password+"&useUnicode=true&characterEncoding=utf8";
		System.out.println("connect " + dbName);
		// System.out.println(jdbcUrl);
		try {
			Class.forName(driverName);
			this.connection = DriverManager.getConnection(jdbcUrl);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.print(e);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.print(e);
			System.exit(0);
		}
	}

	public ArrayList<String> getSelectList() {
		return selectList;
	}

	public void setSelectList(String table, String column) {
		this.selectList.clear();
		String sql = "SELECT * FROM " + table;
		System.out.println(sql);

		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery(sql);
			try {
				while (resultSet.next()) {
					String word = resultSet.getString(column);
					this.selectList.add(word);
					// System.out.println(userId);
				}
			} catch (SQLException q) {
				connection.commit();
				ps.close();
				connection.close();
				System.out.println("setSelectList()" + q);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setSelectWhereList(String table, String inputColumn,
			String outputColumn, String whereValue) {
		this.selectWhereList.clear();
		String sql = "SELECT * FROM " + table + " WHERE " + inputColumn + "='"
				+ whereValue + "'";
		System.out.println(sql);
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery(sql);
			try {
				while (resultSet.next()) {
					String word = resultSet.getString(outputColumn);
					this.selectWhereList.add(word);
					// Utility.print("setSelectWhereList",word);
				}
			} catch (SQLException q) {
				connection.commit();
				ps.close();
				connection.close();
				System.out.println("setSelectList()" + q);
			}
		} catch (Exception e) {
			System.out.println("setSelectList()" + e);
			System.exit(0);
		}

	}

	public void setSelectWhereList(String sql, String outputColumn) {
		this.selectWhereList.clear();
		System.out.println(sql);
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery(sql);
			try {
				while (resultSet.next()) {
					String word = resultSet.getString(outputColumn);
					this.selectWhereList.add(word);
					// Utility.print("setSelectWhereList",word);
				}
			} catch (SQLException q) {
				connection.commit();
				ps.close();
				connection.close();
				System.out.println("setSelectList()" + q);
			}
		} catch (Exception e) {
			System.out.println("setSelectList()" + e);
			System.exit(0);
		}

	}

	public ArrayList<String> getSelectWhereList() {
		return this.selectWhereList;
	}

	public ArrayList<String> getRemovedList() {
		return this.removedList;
	}

	public void setRemovedList(ArrayList<String> removeList, String table,
			String column) {
		String sql = null;
		sql = "SELECT "+column+" FROM " + table + " WHERE " + column + "!='" + " " + "'";
		for (int i = 0; i < removeList.size(); i++) {
			sql = sql + " AND " + column + " != '" + removeList.get(i) + "'";
		}
		sql = sql + "LIMIT 5000;";
		System.out.println("removed list:" + sql);
		java.sql.PreparedStatement ps;
		try {
			ps = connection.prepareStatement(sql);
			java.sql.ResultSet resultSet = ps.executeQuery(sql);
			connection.setAutoCommit(false);
			while (resultSet.next()) {
				String removed = resultSet.getString(column);
				this.removedList.add(removed);
			}

			connection.commit();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			System.exit(0);
		}
		sql = null;

	}

	public boolean check(String table, String column, int value) {
		setCount(table, column, String.valueOf(value));
		int count = dbManager.getCount();
		boolean result = false;
		if (count > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public boolean check(String table, String column, String value) {
		setCount(table, column, value);
		int count = dbManager.getCount();
		boolean result = false;
		if (count > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void InsertByText(String path) {
		try {
			File file = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String sql = br.readLine();
			while (sql != null) {
				try {
					System.out.println(sql);
					Statement stmt = getConnection().createStatement();
					stmt.executeUpdate(sql);
					stmt.close();
				} catch (SQLException e) {
					System.out.println("SQLException:" + e.getMessage());
					System.exit(0);
				}
				sql = br.readLine();
			}
			dbManager.close();

			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public int Insert(String sql) throws Exception {
		int result = 0;
		if (this.connection == null) {
			System.err.println("Insert():Please setConnection!!");
			System.exit(-1);
		}
		System.out.println(sql);
		Statement stmt = this.connection.createStatement();
		result = stmt.executeUpdate(sql);
		stmt.close();
		return result;
	}

	public static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	public static String comvertForSql(String message) {
		message = message.replace("'", "’");
		message = message.replace(",", "，");
		message = message.replace("\"", "”");
		
		return message;
		
	}
}
