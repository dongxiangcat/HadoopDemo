package pers.hadoop.demo.sparkscala

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

object RankApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate();

    val conf = spark.sparkContext.hadoopConfiguration
    val fs = FileSystem.get(conf)
    val exists = fs.exists(new Path("/test/rank_result/_SUCCESS"))
    if (exists){
      fs.delete(new Path("/test/rank_result/"))
    }

    val lines = spark.sparkContext.textFile("/test/text.txt")
    lines.flatMap(line => line.split(" ")).map(word => (word,1)).reduceByKey((x,y)=>x+y).map(word_arr => (word_arr._2,word_arr._1)).sortByKey().map(word_arr => (word_arr._2,word_arr._1)).saveAsTextFile("/test/rank_result/")
  }
}
