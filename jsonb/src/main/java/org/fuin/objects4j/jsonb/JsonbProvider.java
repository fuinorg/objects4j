package org.fuin.objects4j.jsonb;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.util.Objects;

/**
 * Helper to allow late initialization of the JSON-B instance.
 */
public final class JsonbProvider implements AutoCloseable {

    private final JsonbConfig config;

    private Jsonb jsonb;

    /**
     * Constructor with config.
     *
     * @param config Config to use.
     */
    public JsonbProvider(JsonbConfig config) {
        this.config = Objects.requireNonNull(config, "config==null");
    }

    /**
     * Returns the JSON-B instance. It will be built on first access.
     *
     * @return Instance.
     */
    public Jsonb jsonb() {
        if (jsonb == null) {
            jsonb = JsonbBuilder.create(config);
        }
        return jsonb;
    }

    @Override
    public void close() {
        if (jsonb != null) {
            try {
                jsonb.close();
            } catch (final Exception ex) {
                throw new IllegalStateException("Failed to close JSON-B instance", ex);
            }
        }
    }

}
