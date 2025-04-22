/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the {@link Objects4JacksonUtils} class.
 */
@SuppressWarnings("java:S1186") // Methods should not be empty is fine here for the test
public class Objects4JacksonUtilsTest {

    @Test
    public void testJacksonJacksonSerializers() {

        final JsonSerializer<Object> a = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };
        final JsonSerializer<Object> b = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };
        final JsonSerializer<Object> c = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };
        final JsonSerializer<Object> d = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };

        assertThat(Objects4JacksonUtils.joinJacksonSerializers(new JsonSerializer<?>[]{})).isEmpty();
        assertThat(Objects4JacksonUtils.joinJacksonSerializers(new JsonSerializer<?>[]{}, a)).containsExactly(a);
        assertThat(Objects4JacksonUtils.joinJacksonSerializers(new JsonSerializer<?>[]{a}, b)).containsExactly(a, b);
        assertThat(Objects4JacksonUtils.joinJacksonSerializers(new JsonSerializer<?>[]{a, b}, c)).containsExactly(a, b, c);
        assertThat(Objects4JacksonUtils.joinJacksonSerializers(new JsonSerializer<?>[]{a, b}, c, d)).containsExactly(a, b, c, d);

    }

    @Test
    public void testJoinJacksonSerializerArrays() {

        final JsonSerializer<Object> a = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };
        final JsonSerializer<Object> b = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };
        final JsonSerializer<Object> c = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };
        final JsonSerializer<Object> d = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            }
        };

        assertThat(Objects4JacksonUtils.joinJacksonSerializerArrays(new JsonSerializer<?>[]{})).isEmpty();
        assertThat(Objects4JacksonUtils.joinJacksonSerializerArrays(new JsonSerializer<?>[]{}, new JsonSerializer<?>[]{a})).containsExactly(a);
        assertThat(Objects4JacksonUtils.joinJacksonSerializerArrays(new JsonSerializer<?>[]{a}, new JsonSerializer<?>[]{b})).contains(a, b);
        assertThat(Objects4JacksonUtils.joinJacksonSerializerArrays(new JsonSerializer<?>[]{a, b}, new JsonSerializer<?>[]{c})).contains(a, b, c);
        assertThat(Objects4JacksonUtils.joinJacksonSerializerArrays(new JsonSerializer<?>[]{a, b}, new JsonSerializer<?>[]{c, d})).contains(a, b, c, d);
        assertThat(Objects4JacksonUtils.joinJacksonSerializerArrays(new JsonSerializer<?>[]{a}, new JsonSerializer<?>[]{b}, new JsonSerializer<?>[]{c})).contains(a, b, c);

    }

    @Test
    public void testJoinJacksonDeserializers() {

        final JsonDeserializer<Object> a = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };
        final JsonDeserializer<Object> b = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };
        final JsonDeserializer<Object> c = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };
        final JsonDeserializer<Object> d = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };

        assertThat(Objects4JacksonUtils.joinJacksonDeserializers(new JsonDeserializer<?>[]{})).isEmpty();
        assertThat(Objects4JacksonUtils.joinJacksonDeserializers(new JsonDeserializer<?>[]{}, a)).containsExactly(a);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializers(new JsonDeserializer<?>[]{a}, b)).containsExactly(a, b);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializers(new JsonDeserializer<?>[]{a, b}, c)).containsExactly(a, b, c);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializers(new JsonDeserializer<?>[]{a, b}, c, d)).containsExactly(a, b, c, d);

    }

    @Test
    public void testJoinJacksonDeserializerArrays() {

        final JsonDeserializer<Object> a = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };
        final JsonDeserializer<Object> b = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };
        final JsonDeserializer<Object> c = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };
        final JsonDeserializer<Object> d = new JsonDeserializer<Object>() {
            @Override
            public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return null;
            }
        };

        assertThat(Objects4JacksonUtils.joinJacksonDeserializerArrays(new JsonDeserializer<?>[]{})).isEmpty();
        assertThat(Objects4JacksonUtils.joinJacksonDeserializerArrays(new JsonDeserializer<?>[]{}, new JsonDeserializer<?>[]{a})).containsExactly(a);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializerArrays(new JsonDeserializer<?>[]{a}, new JsonDeserializer<?>[]{b})).contains(a, b);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializerArrays(new JsonDeserializer<?>[]{a, b}, new JsonDeserializer<?>[]{c})).contains(a, b, c);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializerArrays(new JsonDeserializer<?>[]{a, b}, new JsonDeserializer<?>[]{c, d})).contains(a, b, c, d);
        assertThat(Objects4JacksonUtils.joinJacksonDeserializerArrays(new JsonDeserializer<?>[]{a}, new JsonDeserializer<?>[]{b}, new JsonDeserializer<?>[]{c})).contains(a, b, c);

    }

    @Test
    public void testDeserialize() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper().registerModule(new TestModule());
        final ContainerExample example = mapper.readValue("""
                {
                    "className" : "org.fuin.objects4j.jackson.Objects4JacksonUtilsTest$Foo",
                    "instance" : {
                        "name" : "Bar"
                    }
                }
                """, ContainerExample.class);
        assertThat(example).isNotNull();
        assertThat(example.className()).isEqualTo(Foo.class.getName());
        assertThat(example.instance()).isInstanceOf(Foo.class);
        assertThat(((Foo) example.instance()).name()).isEqualTo("Bar");
    }

    public record ContainerExample(String className, Object instance) {
    }

    public record Foo(String name) {
    }

    public static final class ContainerExampleDeserializer extends StdDeserializer<ContainerExample> {

        public ContainerExampleDeserializer() {
            super(Currency.class);
        }

        @Override
        public ContainerExample deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            final JsonNode node = jp.getCodec().readTree(jp);
            final String className = node.get("className").asText();
            final JsonNode instanceNode = node.get("instance");
            final Object instance = Objects4JacksonUtils.deserialize(jp, ctxt, className, instanceNode);
            return new ContainerExample(className, instance);
        }

    }

    public static final class TestModule extends Module {

        @Override
        public String getModuleName() {
            return "TestModule";
        }

        @Override
        public Version version() {
            return new Version(1, 0, 0, null);
        }

        @Override
        public void setupModule(SetupContext context) {
            final SimpleDeserializers deserializers = new SimpleDeserializers();
            deserializers.addDeserializer(ContainerExample.class, new ContainerExampleDeserializer());
            context.addDeserializers(deserializers);
        }

    }

}

