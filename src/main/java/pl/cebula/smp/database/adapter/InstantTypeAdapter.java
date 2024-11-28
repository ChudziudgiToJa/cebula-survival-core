package pl.cebula.smp.database.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Instant;

public class InstantTypeAdapter extends TypeAdapter<Instant> {
    @Override
    public void write(JsonWriter out, Instant value) throws IOException {
        out.value(value.getEpochSecond());
    }

    @Override
    public Instant read(JsonReader in) throws IOException {
        long seconds = in.nextLong();
        return Instant.ofEpochSecond(seconds);
    }
}
