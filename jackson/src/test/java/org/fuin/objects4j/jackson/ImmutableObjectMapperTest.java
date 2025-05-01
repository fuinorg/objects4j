package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.fuin.objects4j.core.EmailAddress;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test for the {@link ImmutableObjectMapper.Builder} class.
 */
class ImmutableObjectMapperTest {

    @Test
    void testProvider() throws IOException {

        final ImmutableObjectMapper.Builder builder = new ImmutableObjectMapper.Builder(new ObjectMapper());
        final ImmutableObjectMapper.Provider provider = new ImmutableObjectMapper.Provider(builder);
        builder.registerModule(new Objects4JJacksonModule());

        final EmailAddress emailAddress = new EmailAddress("oh-no@mowhere.com");
        String str = provider.writer().writeValueAsString(emailAddress);
        assertThat(str).isEqualTo("\"oh-no@mowhere.com\"");
        assertThat(provider.reader().readValue(str, EmailAddress.class)).isEqualTo(emailAddress);

        assertThatThrownBy(() -> builder.registerModule(new SimpleModule("Bar")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The object mapper was already built. Modifications are not allowed anymore.");

    }

    @Test
    void testBuilder() throws IOException {

        final ImmutableObjectMapper.Builder builder = new ImmutableObjectMapper.Builder(new ObjectMapper());
        builder.registerModule(new Objects4JJacksonModule());
        final ImmutableObjectMapper mapper = builder.build();

        assertThatThrownBy(() -> builder.registerModule(new SimpleModule("Bar")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The object mapper was already built. Modifications are not allowed anymore.");

        final EmailAddress emailAddress = new EmailAddress("oh-no@mowhere.com");
        String str = mapper.writer().writeValueAsString(emailAddress);
        assertThat(str).isEqualTo("\"oh-no@mowhere.com\"");
        assertThat(mapper.reader().readValue(str, EmailAddress.class)).isEqualTo(emailAddress);

    }

}