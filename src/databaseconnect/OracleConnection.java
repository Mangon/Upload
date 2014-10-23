package databaseconnect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * 控制Oracle连接类
 * 
 * @author gaoxiang
 * 
 */
public class OracleConnection {

	// 数据库驱动对象
	public static String DRIVER;
	// 数据库连接地址(数据库名)
	public static String URL;
	// 登陆名
	public static String USER;
	// 登陆密码
	public static String PWD;
	// 创建数据库连接对象
	private Connection con = null;
	// 创建数据库预编译对象
	private PreparedStatement ps = null;
	// 创建结果集
	private ResultSet rs = null;
	// 创建数据源对象
	public static DataSource source = null;

	public OracleConnection() {

		DRIVER = "oracle.jdbc.driver.OracleDriver";
		URL = "jdbc:oracle:thin:@10.124.0.41:1521:ngess";
		USER = "UCR_CEN1";
		PWD = "cbss_UCR_CEN1";

	}

	/**
	 * 从properties文件获取数据库信息
	 * @param filepath peroperties文件路径，properties文件需要包括<br>
	 * --<i><b>DRIBER</b></i> 数据库驱动对象<br>
	 * --<i><b>URL</b></i> 数据库连接地址(数据库名)<br>
	 * --<i><b>USER</b></i> 登陆名<br>
	 * --<i><b>PWD</b></i> 登陆密码<br>
	 */
	public OracleConnection(String filepath) {
		try {
			File propertiesFile = new File(filepath);
			FileInputStream fileInputStream = null;
			fileInputStream = new FileInputStream(propertiesFile);
			Properties p = new Properties();
			p.load(fileInputStream);

			DRIVER = p.getProperty("DRIVER", "");
			URL = p.getProperty("URL", "");
			USER = p.getProperty("USER", "");
			PWD = p.getProperty("PWD", "");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 */
	public Connection getCon() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(URL, USER, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 关闭所有资源
	 */
	public void closeAll() {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @param sql数据库更新(增、删、改)语句
	 * @param pras参数列表（可传，可不传，不传为NULL，以数组形式存在）
	 * @return 返回受影响的行数
	 */
	public synchronized int update(String sql, String... pras) {
		int resu = 0;
		con = getCon();
		try {
			ps = con.prepareStatement(sql);
			for (int i = 0; i < pras.length; i++) {
				ps.setString(i + 1, pras[i]);
			}
			resu = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return resu;
	}

	/**
	 * @param sql数据库查询语句
	 * @param pras参数列表（可传，可不传，不传为NULL，以数组形式存在）
	 * @return 返回结果集
	 */
	public ResultSet query(String sql, String... pras) {
		con = getCon();
		try {
			ps = con.prepareStatement(sql);
			if (pras != null)
				for (int i = 0; i < pras.length; i++) {
					ps.setString(i + 1, pras[i]);
				}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
