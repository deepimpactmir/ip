package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/**
 * Represents a date and time.
 */
public class DateTime implements Comparable<DateTime> {
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
            .optionalStart().appendPattern(" HHmm").optionalEnd().parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0).toFormatter();
    private static final DateTimeFormatter[] formatters = { formatter,
            DateTimeFormatter.ofPattern("MMM dd yyyy HHmm") };
    private String input;
    private LocalDateTime dateTime;

    /**
     * Initialises and interpret the input string into LocalDateTime.
     *
     * @param input
     */
    public DateTime(String input) {
        this.input = input;
        for (DateTimeFormatter formatter : formatters) {
            try {
                dateTime = LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                // Try another format
            }
        }
    }

    @Override
    public int compareTo(DateTime otherDateTime) {
        return this.dateTime.compareTo(otherDateTime.dateTime);
    }

    @Override
    public String toString() {
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
    }
}
