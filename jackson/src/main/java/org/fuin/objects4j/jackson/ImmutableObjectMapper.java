package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Objects;

/**
 * Thread-safe access to Jackson's {@link com.fasterxml.jackson.databind.ObjectMapper}.
 *
 * @param reader Reader that is thread-safe to use.
 * @param writer Writer that is thread-safe to use.
 */
@ThreadSafe
public record ImmutableObjectMapper(ObjectReader reader, ObjectWriter writer) {

    /**
     * Helper to allow late initialization of the mapper.
     * <p>
     * To make the provider thread-safe for processing you need to finalize the instance
     * by calling the {@link #mapper()} once your application startup has finished.
     * For Spring and Quarkus a good option is to have another "@ApplicationScope"
     * bean with a "@PostConstruct" method that will do it.
     * </p>
     */
    @NotThreadSafe
    public static final class Provider {

        private final Builder builder;

        private ImmutableObjectMapper mapper;

        /**
         * Constructor with builder.
         *
         * @param builder Builder to use.
         */
        public Provider(Builder builder) {
            this.builder = Objects.requireNonNull(builder, "builder==null");
        }

        /**
         * Returns the mapper. The instance will be built on first access.
         *
         * @return Instance.
         */
        public ImmutableObjectMapper mapper() {
            if (mapper == null) {
                mapper = builder.build();
            }
            return mapper;
        }

        /**
         * Convenience method to return the reader.
         *
         * @return Reader.
         */
        public ObjectReader reader() {
            return mapper().reader();
        }

        /**
         * Convenience method to return the writer.
         *
         * @return Writer.
         */
        public ObjectWriter writer() {
            return mapper().writer();
        }

    }

    /**
     * Builds an instance of the outer class.
     */
    @NotThreadSafe
    public static final class Builder {

        private final ObjectMapper mapper;

        private boolean built;

        /**
         * Constructor with mapper.
         *
         * @param mapper Delegate.
         */
        public Builder(ObjectMapper mapper) {
            this.mapper = Objects.requireNonNull(mapper, "mapper==null");
            built = false;
        }

        /**
         * Registers a module.
         *
         * @param module Module to register.
         */
        public void registerModule(Module module) {
            ensureNotBuilt();
            mapper.registerModule(module);
        }

        /**
         * Creates an immutable object mapper. After calling this method the builder cannot be used anymore.
         *
         * @return Instance.
         */
        public ImmutableObjectMapper build() {
            ensureNotBuilt();
            built = true;
            return new ImmutableObjectMapper(mapper.reader(), mapper.writer());
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException("The object mapper was already built. Modifications are not allowed anymore.");
            }
        }

    }


}
