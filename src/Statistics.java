import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private int noBot;
    private static final int GOOD_CODE = 200;
    private static final int BAD_CODE_4xx = 4;
    private static final int BAD_CODE_5xx = 5;
    private HashSet<String> existingPage;
    private HashSet<String> nonexistentPage;
    private HashSet<String> ipAddress;
    private HashMap<String, Integer> operatingSystem;
    private HashMap<String, Integer> usersBrowser;



    public Statistics() {
        totalTraffic = 0;
        minTime = null;
        maxTime = null;
        noBot = 0;
        existingPage = new HashSet<>();
        operatingSystem = new HashMap<>();
        nonexistentPage = new HashSet<>();
        usersBrowser = new HashMap<>();
        ipAddress = new HashSet<>();
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getSizeData();
        if (minTime == null || logEntry.getDateAndTime().isBefore(minTime)) {
            minTime = logEntry.getDateAndTime();
        } else if (maxTime == null || logEntry.getDateAndTime().isAfter(maxTime)) {
            maxTime = logEntry.getDateAndTime();
        }

        String responseCode = "" + logEntry.getCodeResponse();
        if(logEntry.getCodeResponse() == GOOD_CODE){
            existingPage.add(logEntry.getPath());
        } else if(responseCode.startsWith((String.valueOf(BAD_CODE_4xx)))
                || responseCode.startsWith(String.valueOf(BAD_CODE_5xx))){
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

        boolean isBot = logEntry.getUserAgent().isBot();
        if (!isBot){
            noBot +=1;
        }
        String ipAdd = logEntry.getIpAdd();
        if (!isBot){
            ipAddress.add(ipAdd);
        }
    }

    public long getTrafficRate() {
        double durationHours = getTimeInHoursForAllEntriesInLogFile();
        return BigDecimal.valueOf(totalTraffic).divide(BigDecimal.valueOf(durationHours), RoundingMode.HALF_UP).longValue();
    }
    private double getTimeInHoursForAllEntriesInLogFile(){
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

    public double getRealVisitUserIsHours(){
        double hours = getTimeInHoursForAllEntriesInLogFile();
        return hours/ noBot;
    }

    public double getRealVisitUserInvalidIsHours(){
        double hours = getTimeInHoursForAllEntriesInLogFile();
        int invalid = getSizeNonexistentPage();
        return hours/ (double) getSizeNonexistentPage();
    }

    public double getVisitOneUser(){
        return (double) noBot / (double) ipAddress.size();
    }


    private int getSizeNonexistentPage(){
        return nonexistentPage.size();
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
