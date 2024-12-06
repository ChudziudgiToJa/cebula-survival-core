package pl.cebula.smp.util;

import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class DurationUtil {

    private DurationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String format(@NonNull final Instant instant) {
        return format(instant, ZoneId.of("Poland"));
    }

    public static String format(@NonNull final Instant instant, @NonNull final ZoneId zoneId) {
        return format(instant, zoneId, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(@NonNull final Instant instant, @NonNull final ZoneId zoneId, @NonNull final String pattern) {
        final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return zonedDateTime.format(formatter);
    }

    public static long getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.getTimeInMillis();
    }

    public static String convertLong(long seconds) {
        long d = TimeUnit.SECONDS.toDays(seconds);
        long h = TimeUnit.SECONDS.toHours(seconds) - d * 24L;
        long m = TimeUnit.SECONDS.toMinutes(seconds) - d * 24L * 60L - h * 60L;
        long s = seconds - d * 24L * 60L * 60L - h * 60L * 60L - m * 60L;
        if (d != 0L)
            return d + "d " + h + "h " + m + "min " + s + "s";
        if (h != 0L)
            return h + "h " + m + "min " + s + "s";
        if (m != 0L)
            return m + "min " + s + "s";
        return s + "s";
    }

    public static String format(Temporal temporal) {
        Instant now = Instant.now();

        return format(Duration.between(temporal, now));
    }

    public static String format(Duration duration) {
        return format(duration, true);
    }

    public static String format(Duration duration, boolean removeMillis) {
        if (removeMillis) {
            duration = Duration.ofSeconds(duration.toSeconds());
        }

        long days = duration.toDaysPart();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        long millis = duration.toMillisPart();

        StringBuilder formattedDuration = new StringBuilder();

        if (days > 0) {
            formattedDuration.append(days).append(MessageUtil.smallText("d "));
        }
        if (hours > 0) {
            formattedDuration.append(hours).append(MessageUtil.smallText("g "));
        }
        if (minutes > 0) {
            formattedDuration.append(minutes).append(MessageUtil.smallText("m "));
        }
        if (seconds > 0 || formattedDuration.isEmpty()) {
            formattedDuration.append(seconds).append(MessageUtil.smallText("s"));
        }
        if (seconds == 0 && millis > 0) {
            formattedDuration.append(" ").append(millis).append(MessageUtil.smallText("ms"));
        }

        return formattedDuration.toString().trim();
    }

    public static String parseLong(final long milliseconds, final boolean abbreviate) {
        final List<String> units = new ArrayList<String>();
        long amount = milliseconds / 604800000L;
        units.add(amount + "w");
        amount = milliseconds / 86400000L % 7L;
        units.add(amount + "d");
        amount = milliseconds / 3600000L % 24L;
        units.add(amount + "h");
        amount = milliseconds / 60000L % 60L;
        units.add(amount + "m");
        amount = milliseconds / 1000L % 60L;
        units.add(amount + "s");
        final String[] array = new String[5];
        for (final String s : units) {
            final char end = s.charAt(s.length() - 1);
            switch (end) {
                case 'w': {
                    array[0] = s;
                }
                case 'd': {
                    array[1] = s;
                }
                case 'h': {
                    array[2] = s;
                }
                case 'm': {
                    array[3] = s;
                }
                case 's': {
                    array[4] = s;
                    continue;
                }
                default: {
                    continue;
                }
            }
        }
        units.clear();
        for (final String s2 : array) {
            if (!s2.startsWith("0")) {
                units.add(s2);
            }
        }
        final StringBuilder sb = new StringBuilder();
        for (final String s3 : units) {
            if (!abbreviate) {
                final char c = s3.charAt(s3.length() - 1);
                final String count = s3.substring(0, s3.length() - 1);
                String word = null;
                switch (c) {
                    case 'w': {
                        word = "t";
                        break;
                    }
                    case 'd': {
                        word = "d";
                        break;
                    }
                    case 'h': {
                        word = "g";
                        break;
                    }
                    case 'm': {
                        word = "m";
                        break;
                    }
                    default: {
                        word = "s";
                        break;
                    }
                }
                final String and = s3.equals(units.get(units.size() - 1)) ? "" : (s3.equals(units.get(units.size() - 2)) ? " i " : ", ");
                sb.append(count).append(word).append(and);
            }
            else {
                sb.append(s3);
                if (s3.equals(units.get(units.size() - 1))) {
                    continue;
                }
                sb.append("-");
            }
        }
        return (sb.toString().trim().length() == 0) ? "NULL" : sb.toString().trim();
    }

    public static String getTimeFormat(long milliseconds) {
        long days = milliseconds / (24 * 60 * 60 * 1000);
        milliseconds %= (24 * 60 * 60 * 1000);

        long hours = milliseconds / (60 * 60 * 1000);
        milliseconds %= (60 * 60 * 1000);

        long minutes = milliseconds / (60 * 1000);
        milliseconds %= (60 * 1000);

        long seconds = milliseconds / 1000;
        milliseconds %= 1000;

        long millis = milliseconds;

        StringBuilder formattedTime = new StringBuilder();
        if (days != 0) formattedTime.append(days).append("d ");
        if (hours != 0) formattedTime.append(hours).append("h ");
        if (minutes != 0) formattedTime.append(minutes).append("m ");
        if (seconds != 0) formattedTime.append(seconds).append("s ");
//        if (millis != 0) formattedTime.append(millis).append("ms");
        if (!formattedTime.isEmpty() && formattedTime.charAt(formattedTime.length() - 1) == ' ') {
            formattedTime.deleteCharAt(formattedTime.length() - 1);
        }
        return formattedTime.toString();
    }

}
