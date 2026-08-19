package zaru.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import zaru.exception.ZaruException;

/** Tests date-time parsing and formatting used by deadline and event tasks. */
public class DateTimeParserTest {
    @Test
    public void parse_dateTimeInput_returnsDateTime() throws ZaruException {
        LocalDateTime result = DateTimeParser.parse("2026-08-19 1430");

        assertEquals(LocalDateTime.of(2026, 8, 19, 14, 30), result);
    }

    @Test
    public void parse_dateOnlyInput_returnsMidnight() throws ZaruException {
        LocalDateTime result = DateTimeParser.parse("2026-08-19");

        assertEquals(LocalDateTime.of(2026, 8, 19, 0, 0), result);
    }

    @Test
    public void format_dateTime_returnsDisplayFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 8, 19, 14, 30);

        assertEquals("Aug 19 2026, 2:30PM", DateTimeParser.format(dateTime));
    }

    @Test
    public void formatForStorage_dateTime_returnsStableStorageFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 8, 19, 14, 30);

        assertEquals("2026-08-19 1430", DateTimeParser.formatForStorage(dateTime));
    }

    @Test
    public void parse_blankInput_exceptionThrown() {
        ZaruException exception = assertThrows(ZaruException.class, () -> DateTimeParser.parse(" "));

        assertEquals("Please provide a date and time.", exception.getMessage());
    }

    @Test
    public void parse_invalidInput_exceptionThrown() {
        ZaruException exception = assertThrows(ZaruException.class,
                () -> DateTimeParser.parse("19-08-2026"));

        assertEquals("Please enter dates in yyyy-MM-dd or yyyy-MM-dd HHmm format.", exception.getMessage());
    }
}
