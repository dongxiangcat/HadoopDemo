package pers.hadoop.demo.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.awt.image.ImagingOpException;
import java.io.IOException;

/**
 * @ProjectName HadoopDemo
 * @Package pers.hadoop.demo.mapreduce.wordcount
 * @ClassName WordCountApp
 * @Description TODO
 * @Company HaoBo
 * @Author user
 * @Date 2020-02-18 15:42
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class WordCountApp {
    public static void main(String[] args) throws IOException,InterruptedException,ClassNotFoundException {

        // 创建配置
        Configuration configuration = new Configuration();

        // 清理已经存在的输出目录
        Path output = new Path(args[1]);
        FileSystem fileSystem = FileSystem.get(configuration);
        if (fileSystem.exists(output)){
            fileSystem.delete(output);
            System.out.println("output file exists, but is has deleted");
        }

        // 创建JOB
        Job job = Job.getInstance(configuration);

        //设置job的处理类
        job.setJarByClass(WordCountApp.class);

        // 配置mapper
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // 配置reducer
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //设置作业处理的输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        // 配置输出路径
        FileOutputFormat.setOutputPath(job,output);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
