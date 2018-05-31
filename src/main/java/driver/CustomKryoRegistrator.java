package driver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import data.Episodes;
import org.apache.spark.serializer.KryoRegistrator;

public class CustomKryoRegistrator implements KryoRegistrator {

    @Override
    public void registerClasses(Kryo kryo) {

        kryo.register(Episodes.class, new FieldSerializer(kryo, Episodes.class));

    }
}
