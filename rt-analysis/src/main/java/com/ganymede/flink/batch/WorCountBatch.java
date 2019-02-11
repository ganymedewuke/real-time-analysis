package com.ganymede.flink.batch;

import com.ganymede.flink.batch.util.WordCountData;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;

public class WorCountBatch {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        //初始化批处理环境
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        //获取输入的数据
        DataSet<String> text;
        if (params.has("input")) {
            // read the text file from given input path
            // 从输入的路径，读取文件
            text = env.readTextFile(params.get("input"));
        } else {
            // get default test text data
            //如果没有输入文件路径 ， 获取默认的数据
            System.out.println("Executing WordCount example with default input data set.");
            System.out.println("Use --input to specify file input.");
            text = WordCountData.getDefaultTextLineDataSet(env);
        }

        //从输入数据中，产生数据集
        // 返回两个元素的元组对象 Tuple2
        DataSet<Tuple2<String, Integer>> counts =
                // split up the lines in pairs (2-tuples) containing: (word,1)
                text.flatMap(new Tokenizer())
                        // group by the tuple field "0" and sum up tuple field "1"
                        // 对第0个字段进行 group by
                        .groupBy(0)
                        // 对第1个字段进行 sum
                        .sum(1);

        // emit result
        if (params.has("output")) {
            //如果有输出路径 ，则落地到文件
            counts.writeAsCsv(params.get("output"), "\n", " ");
            // execute program
            env.execute("WordCount Example");
        } else {
            // 没有输出路径，则直接打印数据
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            counts.print();
        }

    }

    // *************************************************************************
    //     USER FUNCTIONS
    // *************************************************************************

    /**
     * Implements the string tokenizer that splits sentences into words as a user-defined
     * FlatMapFunction. The function takes a line (String) and splits it into
     * multiple pairs in the form of "(word,1)" ({@code Tuple2<String, Integer>}).
     */
    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            // 正则表达式，截出单词，并除去标点等特殊符号
            String[] tokens = value.toLowerCase().split("\\W+");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    //返回两个元素的元组，元素一为单词，元素二为记录数
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        }
    }
}
