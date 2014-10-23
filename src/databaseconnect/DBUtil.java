package databaseconnect;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
	public static List<Map<String, Object>> resultSetToList(ResultSet rs)
			throws java.sql.SQLException {
		if (rs == null)
			return Collections.emptyList();
		ResultSetMetaData md = rs.getMetaData(); //�õ������(rs)�Ľṹ��Ϣ�������ֶ������ֶ�����   
		int columnCount = md.getColumnCount(); //���ش� ResultSet �����е�����   
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowData = new HashMap<String, Object>();
		while (rs.next()) {
			rowData = new HashMap<String, Object>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		System.out.println("list:" + list.toString());
		return list;
	}
}
