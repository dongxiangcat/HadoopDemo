package pers.hadoop.demo.mapreduce.rank;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ProjectName HadoopDemo
 * @Package pers.hadoop.demo.mapreduce.rank
 * @ClassName MyCombiner
 * @Description TODO
 * @Company HaoBo
 * @Author user
 * @Date 2020-02-20 14:18
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class MyCombiner extends Reducer<Text, LongWritable,Text,LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (LongWritable value : values){
            sum += value.get();
        }
        context.write(key,new LongWritable(sum));
    }
}
