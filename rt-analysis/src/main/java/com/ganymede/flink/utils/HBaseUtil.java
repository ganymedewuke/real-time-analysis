package com.ganymede.flink.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HBaseUtil {
	private static Admin admin = null;
	private static Connection conn = null;

	/**
	 * 静态代码块
	 * 初始化Hbase连接
	 */
	static {
		//创建hbase配置对象
		Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.rootdir", "hdfs://spark1:8020/hbase");
		//zk配置
		configuration.set("hbase.zookeeper.quorum", "spark1,spark2,spark3");
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("hbase.client.scanner.timeout.period", "600000");
		configuration.set("hbaee.rpc.timeout", "600000");

		try {
			conn = ConnectionFactory.createConnection(configuration);
			//得到管理程序
			admin = conn.getAdmin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建表
	 *
	 * @param tabName
	 * @param familyName
	 * @throws IOException
	 */
	public void createTable(String tabName, String familyName) throws IOException {
		HTableDescriptor tab = new HTableDescriptor(tabName);
		//添加列族，每个表至少有一个列族
		HColumnDescriptor colDesc = new HColumnDescriptor(familyName);
		tab.addFamily(colDesc);
		//创建表
		admin.createTable(tab);
	}

	/**
	 * hbase 播入批量数据
	 *
	 * @param tableName
	 * @param rowkey
	 * @param familyName
	 * @param dataMap
	 */
	public static void put(String tableName, String rowkey, String familyName, Map<String, String> dataMap) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableName));
		//将字符串转换成byte[]
		byte[] rowkeyByte = Bytes.toBytes(rowkey);
		Put put = new Put(rowkeyByte);

		if (dataMap != null) {
			Set<Map.Entry<String, String>> set = dataMap.entrySet();
			for (Map.Entry<String, String> entry : set) {
				String key = entry.getKey();
				Object value = entry.getValue();
				put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(key), Bytes.toBytes(value + ""));
			}
		}
		table.put(put);
		table.close();
	}

	/**
	 * 获取数据
	 *
	 * @param tableName
	 * @param rowKey
	 * @param familyName
	 * @param colum
	 * @return
	 * @throws IOException
	 */
	public static String getData(String tableName, String rowKey, String familyName, String colum) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableName));
		//将字符串转换成byte[]
		byte[] rowKeyByte = Bytes.toBytes(rowKey);
		Get get = new Get(rowKeyByte);
		Result result = table.get(get);
		byte[] resultbytes = result.getValue(familyName.getBytes(), colum.getBytes());

		if (resultbytes == null) {
			return null;
		}
		return new String(resultbytes);
	}

	/**
	 * 插入单条数据
	 *
	 * @param tableName
	 * @param rowKey
	 * @param familyName
	 * @param colum
	 * @param data
	 * @throws IOException
	 */
	public static void putData(String tableName, String rowKey, String familyName, String colum, String data) throws IOException {
		Table table = conn.getTable(TableName.valueOf(tableName));
		Put put = new Put(rowKey.getBytes());
		put.addColumn(familyName.getBytes(), colum.getBytes(), data.getBytes());
		table.put(put);
	}

	public static void main(String[] args) throws IOException {
		Map<String,String> map = new HashMap();
		map.put("m1","xx");
		map.put("m2","xx2");
		map.put("m3","你好，谢谢");

		put("baseuserscaninfo", "a4", "time", map);


		putData("baseuserscaninfo", "a3", "time", "sys", "你好，谢谢");


		String result = getData("baseuserscaninfo", "a3", "time", "sys");
		System.out.println(result);
	}

}
