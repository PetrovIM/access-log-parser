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
    private HashSet<String> nonexistentPage;
    private HashMap<String, Integer> operatingSystem;
    private HashMap<String, Integer> usersBrowser;


    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        existingPage = new HashSet<>();
        operatingSystem = new HashMap<>();
        nonexistentPage = new HashSet<>();
        usersBrowser = new HashMap<>();
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
        } else if(logEntry.getCodeResponse() == 404){
            nonexistentPage.add(logEntry.getPath());
        }

        String logEntryOsType = logEntry.getUserAgent().getTypeOS();
        if (operatingSystem.containsKey(logEntryOsType)) {
            int counter = operatingSystem.get(logEntryOsType);
            operatingSystem.replace(logEntryOsType, ++counter);
        } else {
            operatingSystem.put(logEntryOsType, 1);
        }

        String logEntryBrowserType = logEntry.getUserAgent().getBrowser();
        if (usersBrowser.containsKey(logEntryBrowserType)){
            int counter = usersBrowser.get(logEntryBrowserType);
            usersBrowser.replace(logEntryBrowserType,++counter);
        }else {
            usersBrowser.put(logEntryBrowserType, 1);
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

    public List<String> getAllNonexistentPage(){
        return new ArrayList<>(nonexistentPage);
    }

    public HashMap <String, Double> getOperationOsSystem(){
        HashMap<String,Double> operationOsSystem = new HashMap<>();
        int osCounter = Calculator(operatingSystem);
        for (Map.Entry<String,Integer> entry : operatingSystem.entrySet()){
            operationOsSystem.put(entry.getKey(), ((double) entry.getValue()/(double) osCounter));
        }
        return operationOsSystem;
    }

    public HashMap<String, Double> getUserBrowser(){
        HashMap <String,Double> userBrowser = new HashMap<>();
        int browserCounter = Calculator(usersBrowser);
        for (Map.Entry<String,Integer> browser : usersBrowser.entrySet()){
            userBrowser.put(browser.getKey(), ((double) browser.getValue()/ (double) browserCounter));
        }
        return userBrowser;
    }

    public int Calculator(HashMap<String ,Integer> inputSet){
        int counter = 0;
        for (Map.Entry<String,Integer> set : inputSet.entrySet()){
            counter += set.getValue();
        }
        return counter;
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


    public HashSet<String> getNonexistentPage() {
        return nonexistentPage;
    }

    public HashMap<String, Integer> getUsersBrowser() {
        return usersBrowser;
    }
}
