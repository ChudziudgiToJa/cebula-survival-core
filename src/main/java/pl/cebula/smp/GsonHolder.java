package pl.cebula.smp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.cebula.smp.database.adapter.ClassTypeAdapter;
import pl.cebula.smp.database.adapter.DurationTypeAdapter;
import pl.cebula.smp.database.adapter.InstantTypeAdapter;

import java.time.Duration;
import java.time.Instant;

public final class GsonHolder {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeHierarchyAdapter(Class.class, new ClassTypeAdapter())
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
            .setPrettyPrinting()
            .create();
}
