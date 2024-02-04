package org.fuin.objects4j.jsonb;

import jakarta.json.bind.adapter.JsonbAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility methods for JSON-B.
 */
public final class JsonbUtils {

    private static final List<JsonbAdapter<?, ?>> ADAPTERS = List.of(
            new DayOfTheWeekJsonbAdapter(),
            new HourRangesJsonbAdapter(),
            new LocaleJsonbAdapter(),
            new EmailAddressJsonbAdapter(),
            new PasswordJsonbAdapter(),
            new CurrencyAmountJsonbAdapter(),
            new WeeklyOpeningHoursJsonbAdapter(),
            new UUIDJsonbAdapter(),
            new HourRangeJsonbAdapter(),
            new MultiDayOfTheWeekJsonbAdapter(),
            new UserNameJsonbAdapter(),
            new HourJsonbAdapter(),
            new DayOpeningHoursJsonbAdapter(),
            new PasswordSha512JsonbAdapter(),
            new CurrencyJsonbAdapter()
    );

    private JsonbUtils() {
    }

    /**
     * Returns the list of {@link JsonbAdapter} objects defined by the package.
     *
     * @return New instance of the adapter list.
     */
    public static List<JsonbAdapter<?, ?>> getJsonbAdapters() {
        return new ArrayList<>(ADAPTERS);
    }

}
