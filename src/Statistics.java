import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getSizeData();
        if (minTime == null || logEntry.getDateAndTime().isBefore(minTime)) {
            minTime = logEntry.getDateAndTime();
        } else if (maxTime == null || logEntry.getDateAndTime().isAfter(maxTime)) {
            maxTime = logEntry.getDateAndTime();
        }
    }

    public long getTrafficRate() {
        Duration durationBetween = Duration.between(minTime, maxTime);
        double hours = durationBetween.toHoursPart();
        double minutesToHours = (double) durationBetween.toMinutesPart() / 60.0;
        double secondsToHours = (double) durationBetween.toSecondsPart() / 360.0;
        double durationInHours = hours + minutesToHours + secondsToHours;

        return BigDecimal.valueOf(totalTraffic).divide(BigDecimal.valueOf(durationInHours), RoundingMode.HALF_UP).longValue();
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
}
