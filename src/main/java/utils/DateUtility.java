package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.ReasearchAndEducationPage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class DateUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtility.class);

    public static boolean areDatesIdenticalWithAdjustedCurrentDate(String dateAsString, String adjustmentType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        LocalDate inputDate;

        try {
            inputDate = LocalDate.parse(dateAsString.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, expected format is 'yyyy MMM dd'", e);
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate adjustedCurrentDate = currentDate;

        adjustedCurrentDate = switch (adjustmentType.toLowerCase()) {
            case "today" -> currentDate;
            case "tomorrow" -> currentDate.plusDays(1);
            case "next week" -> currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
            case "next month" -> currentDate.plusMonths(1).withDayOfMonth(1);
            default -> throw new IllegalArgumentException("Invalid adjustment type, expected 'today', 'tomorrow', 'next week' or 'next month'");
        };

        LOGGER.info("The input date is: {}, the adjusted current date is: {}", inputDate, adjustedCurrentDate);
        return inputDate.isEqual(adjustedCurrentDate);
    }
}