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
 * ����Oracle������
 * 
 * @author gaoxiang
 * 
 */
public class OracleConnection {

	// ���ݿ���������
	public static String DRIVER;
	// ���ݿ����ӵ�ַ(���ݿ���)
	public static String URL;
	// ��½��
	public static String USER;
	// ��½����
	public static String PWD;
	// �������ݿ����Ӷ���
	private Connection con = null;
	// �������ݿ�Ԥ�������
	private PreparedStatement ps = null;
	// ���������
	private ResultSet rs = null;
	// ��������Դ����
	public static DataSource source = null;

	public OracleConnection() {

		DRIVER = "oracle.jdbc.driver.OracleDriver";
		URL = "jdbc:oracle:thin:@10.124.0.41:1521:ngess";
		USER = "UCR_CEN1";
		PWD = "cbss_UCR_CEN1";

	}

	/**
	 * ��properties�ļ���ȡ���ݿ���Ϣ
	 * @param filepath peroperties�ļ�·����properties�ļ���Ҫ����<br>
	 * --<i><b>DRIBER</b></i> ���ݿ���������<br>
	 * --<i><b>URL</b></i> ���ݿ����ӵ�ַ(���ݿ���)<br>
	 * --<i><b>USER</b></i> ��½��<br>
	 * --<i><b>PWD</b></i> ��½����<br>
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
	 * ��ȡ���ݿ�����
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
	 * �ر�������Դ
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
	 * @param sql���ݿ����(����ɾ����)���
	 * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�
	 * @return ������Ӱ�������
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
	 * @param sql���ݿ��ѯ���
	 * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�
	 * @return ���ؽ����
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
