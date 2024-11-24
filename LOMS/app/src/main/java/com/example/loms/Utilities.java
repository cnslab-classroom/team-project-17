package com.example.loms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utilities {

    /**
     * Formats a timestamp into a specified date format.
     *
     * @param timestamp The timestamp in milliseconds.
     * @param pattern   The desired date format pattern.
     * @return A formatted date string.
     */
    public static String formatDate(long timestamp, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            return formatter.format(new Date(timestamp));
        } catch (Exception e) {
            return "Invalid Date";
        }
    }

    /**
     * Formats a timestamp into a specified date format with a given timezone.
     *
     * @param timestamp The timestamp in milliseconds.
     * @param pattern   The desired date format pattern.
     * @param timeZone  The desired timezone.
     * @return A formatted date string.
     */
    public static String formatDateWithTimeZone(long timestamp, String pattern, String timeZone) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
            return formatter.format(new Date(timestamp));
        } catch (Exception e) {
            return "Invalid Date";
        }
    }

    /**
     * Gets the current date in the specified format.
     *
     * @param pattern The desired date format pattern.
     * @return A formatted date string of the current date.
     */
    public static String getCurrentDate(String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            return formatter.format(new Date());
        } catch (Exception e) {
            return "Invalid Date";
        }
    }

    /**
     * Parses a date string into a Date object.
     *
     * @param dateString The date string to parse.
     * @param pattern    The format of the date string.
     * @return A Date object, or null if parsing fails.
     */
    public static Date parseDate(String dateString, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
            return formatter.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
