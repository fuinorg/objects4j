package org.fuin.objects4j.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.fuin.objects4j.core.EmailAddress;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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


    /**
     * A provider is normally shared, so several threads may ask for the mapper at the same time.
     * <p>
     * The builder behind it can be built only once, so an unsynchronized lazy initialisation lets two
     * threads both find no mapper, both build, and one fail with "The object mapper was already built".
     * This showed up in an application whose projections each run on their own thread and start
     * together: one projection died on the first event it tried to read.
     */
    @Test
    void testProviderIsSafeForConcurrentFirstAccess() throws Exception {

        final int threads = 16;
        final ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            for (int attempt = 0; attempt < 100; attempt++) {

                final ImmutableObjectMapper.Provider provider =
                        new ImmutableObjectMapper.Provider(new ImmutableObjectMapper.Builder(new ObjectMapper()));

                // All of them released at once, so the first access really is concurrent.
                final CyclicBarrier start = new CyclicBarrier(threads);
                final List<Callable<ImmutableObjectMapper>> calls = new ArrayList<>();
                for (int i = 0; i < threads; i++) {
                    calls.add(() -> {
                        start.await();
                        return provider.mapper();
                    });
                }

                final List<ImmutableObjectMapper> mappers = new ArrayList<>();
                for (final Future<ImmutableObjectMapper> result : pool.invokeAll(calls)) {
                    // Fails the test with the cause when a thread threw.
                    mappers.add(result.get());
                }

                // Every caller has to get the same instance, not merely "no exception": handing out two
                // mappers would be the same bug wearing a different hat.
                assertThat(mappers).hasSize(threads);
                assertThat(mappers).containsOnly(mappers.get(0));
            }
        } finally {
            pool.shutdownNow();
        }

    }

}
