package org.fuin.objects4j.jsonb;

import jakarta.json.bind.JsonbConfig;
import org.fuin.objects4j.core.EmailAddress;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the {@link JsonbProvider} class.
 */
class JsonbProviderTest {

    @Test
    void testProvider() throws IOException {

        final JsonbConfig config = new JsonbConfig();
        try (final JsonbProvider provider = new JsonbProvider(config)) {
            config.withAdapters(JsonbUtils.getJsonbAdapterArray());

            final EmailAddress emailAddress = new EmailAddress("oh-no@mowhere.com");
            String str = provider.jsonb().toJson(emailAddress);
            assertThat(str).isEqualTo("\"oh-no@mowhere.com\"");
            assertThat(provider.jsonb().fromJson(str, EmailAddress.class)).isEqualTo(emailAddress);

        }
    }


}