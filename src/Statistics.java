import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> existingPage;
    private HashMap<String, Integer> operatingSystem;





    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        existingPage = new HashSet<>();
        operatingSystem = new HashMap<>();
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getSizeData();
        if (minTime == null || logEntry.getDateAndTime().isBefore(minTime)) {
            minTime = logEntry.getDateAndTime();
        } else if (maxTime == null || logEntry.getDateAndTime().isAfter(maxTime)) {
            maxTime = logEntry.getDateAndTime();
        }

        if(logEntry.getCodeResponse() == 200){
            existingPage.add(logEntry.getPath());
        }

        String logEntryOsType = logEntry.getUserAgent().getTypeOS();
        if (operatingSystem.containsKey(logEntryOsType)) {
            int counter = operatingSystem.get(logEntryOsType);
            operatingSystem.replace(logEntryOsType, ++counter);
        } else {
            operatingSystem.put(logEntryOsType, 1);
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

    public List<String> getAllExistingPage() {
        return new ArrayList<>(existingPage);
    }

    public HashMap <String, Double> getOperationOsSystem(){
        HashMap<String,Double> operationOsSystem = new HashMap<>();
        int osCounter = oSCalculator();
        for (Map.Entry<String,Integer> entry : operatingSystem.entrySet()){
            operationOsSystem.put(entry.getKey(), ((double) entry.getValue()/(double) osCounter));
        }
        return operationOsSystem;
    }

    public int oSCalculator(){
        int osCounter = 0;
        for (Map.Entry<String,Integer> set : operatingSystem.entrySet()){
            osCounter += set.getValue();
        }
        return osCounter;
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

    public HashSet<String> getExistingPage() {
        return existingPage;
    }
    public HashMap<String, Integer> getOperatingSystem() {
        return operatingSystem;
    }
}
