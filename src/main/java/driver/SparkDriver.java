package driver;

import static java.lang.String.format;

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

        //Whether to discover the nodes within the ElasticSearch cluster or only to use the ones given
        conf.set("es.nodes.discovery", "false");

        // In this mode, the connector disables discovery and only connects through the declared es.nodes during all operations,
        // including reads and writes. Note that in this mode, performance is highly affected
        conf.set("es.nodes.wan.only", "true");

        //if we want based on the .avro file what we get to be what spark automatically pushes to ES
        conf.set("es.index.auto.create", "true");

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

                LOGGER.info(format("title: %s air_date: %s doctor: %d", title, air_date, doctor));

                return new Episodes(title, air_date, doctor);
            });

        LOGGER.info(format("Number of Row Lines | Records submitted to ES: %d", episodesJavaRDD.count()));

        JavaEsSpark.saveToEs(episodesJavaRDD, "episodes/the-doctors");

        spark.stop();
    }
}
