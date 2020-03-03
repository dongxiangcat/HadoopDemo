package pers.hadoop.demo.mapreduce.rank;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ProjectName HadoopDemo
 * @Package pers.hadoop.demo.mapreduce.rank
 * @ClassName MyReducer
 * @Description TODO
 * @Company HaoBo
 * @Author user
 * @Date 2020-02-20 11:33
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class MyReducer extends Reducer<Text, LongWritable,Text,LongWritable> {
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    private Map<String,Integer> map = new HashMap<>();

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (LongWritable value : values){
            sum += value.get();
        }
        map.put(key.toString(),sum);

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

        /*Map<String,Integer> result = map.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal,newVal) -> oldVal,
                        LinkedHashMap::new
                ));*/

        //这里将map.entrySet()转换成list
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //升序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<String, Integer> entry : list){
            context.write(new Text(entry.getKey()),new LongWritable(entry.getValue()));
        }

    }
}
