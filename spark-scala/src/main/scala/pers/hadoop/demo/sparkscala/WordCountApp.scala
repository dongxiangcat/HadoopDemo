package pers.hadoop.demo.sparkscala

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession


// 在$spark_home/jars下找scala版本
// mvn clean scala:compile compile package
object WordCountApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().enableHiveSupport().getOrCreate()

    val conf = spark.sparkContext.hadoopConfiguration
    val fs = FileSystem.get(conf)
    val exists = fs.exists(new Path("/test/result/_SUCCESS"))
    if (exists){
      fs.delete(new Path("/test/result/"))
    }

    val lines = spark.sparkContext.textFile("/test/text.txt")
    lines.flatMap(line => line.split(" ")).map(word => (word,1)).reduceByKey((x,y)=>x+y).saveAsTextFile("/test/result/")
  }
}
