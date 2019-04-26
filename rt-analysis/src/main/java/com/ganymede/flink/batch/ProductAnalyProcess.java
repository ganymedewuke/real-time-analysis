package com.ganymede.flink.batch;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

public class ProductAnalyProcess {
	public static void main(String[] args) throws Exception {
		final ParameterTool params = ParameterTool.fromArgs(args);

		//初始化批处理环境
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

		// make parameters available in the web interface
		env.getConfig().setGlobalJobParameters(params);

		// get input data
		//获取输入的数据
		DataSet<String> text = env.readTextFile(params.get("input"));


	}
}
