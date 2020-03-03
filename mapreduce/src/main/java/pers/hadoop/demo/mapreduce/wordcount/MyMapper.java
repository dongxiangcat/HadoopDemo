package pers.hadoop.demo.mapreduce.wordcount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ProjectName HadoopDemo
 * @Package pers.hadoop.demo.mapreduce.wordcount
 * @ClassName MyMapper
 * @Description TODO
 * @Company HaoBo
 * @Author user
 * @Date 2020-02-18 15:43
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class MyMapper extends Mapper<LongWritable,Text,Text,LongWritable> {

    LongWritable one = new LongWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for (String word:words){
            context.write(new Text(word),one);
        }
    }
}
