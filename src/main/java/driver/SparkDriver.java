package driver;

import data.Episodes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.util.Utils;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkDriver {

    private static final Logger LOGGER =
        LoggerFactory.getLogger(SparkDriver.class);

    public static void main(String... args) throws ClassNotFoundException {

        SparkConf conf = new SparkConf()
            .setAppName("spark ingestion and es processing")
            .setMaster("local[*]");

        conf.set("es.nodes", "localhost:9200");
        conf.set("es.nodes.discovery", "true");
        conf.set("es.nodes.wan.only", "false");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        conf.set("spark.kryo.registrator", CustomKryoRegistrator.class.getName());
        conf.set("spark.kryoserializer.buffer", "128m");
        conf.registerKryoClasses(new Class<?>[]{
            Class.forName("com.databricks.spark.avro.DefaultSource$SerializableConfiguration")});
        conf.set("spark.kryo.registrationRequired", "true");

        SparkSession spark = SparkSession.builder()
            .config(conf)
            .getOrCreate();

        Dataset<Row> rowDataset = spark
            .read()
            .format("com.databricks.spark.avro")
            .load(Utils.resolveURIs("src\\episodes.avro"));

        JavaRDD<Episodes> episodesJavaRDD = rowDataset
            .javaRDD()
            .map((Function<Row, Episodes>) row -> {

                String title = row.getString(0);
                String air_date = row.getString(1);
                int doctor = row.getInt(2);

                LOGGER.info("title: " + title + " air_date: " + air_date + " doctor: " + doctor);

                return new Episodes(title, air_date, doctor);
            });

        LOGGER.info("Number of Row Lines | Records submitted to ES: " + episodesJavaRDD.count());

        JavaEsSpark.saveToEs(episodesJavaRDD, "episodes/the-doctors");

        spark.stop();
    }
}
