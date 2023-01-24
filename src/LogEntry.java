import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private static final String REGEX = "([\\d]{1,3}[\\.][\\d]{1,3}[\\.][\\d]{1,3}[\\.][\\d]{1,3})\\s([\\-|\\s])+\\s([\\-|\\s])\\s\\[([^]]*)\\]\\s\\\"([^\\\"]*)\\\"\\s([\\d]+)\\s([\\d]+)\\s\\\"([^\\\"]*)\\\"\\s\\\"([^\\\"]*)\\\"";
    private static final String TIME = "dd/MMM/yyyy:HH:mm:ss Z";
    private final String ipAdd;
    private final String propertyOne;
    private final String propertyTwo;
    private final LocalDateTime dateAndTime;
    private final HttpMethod httpMethod;
    private final String path;
    private final int codeResponse;
    private final int sizeData;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String log){
        Pattern pattern = Pattern.compile(REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(log);

        matcher.find();
        String[] methodArray = methodPathParser(matcher.group(5));

        this.ipAdd = matcher.group(1);
        this.propertyOne = matcher.group(2);
        this.propertyTwo = matcher.group(3);
        this.dateAndTime = localDateTime(matcher.group(4));
        this.httpMethod = HttpMethod.valueOf(methodArray[0]);
        this.path = methodArray[1];
        this.codeResponse = Integer.parseInt(matcher.group(6));
        this.sizeData = Integer.parseInt(matcher.group(7));
        this.referer = matcher.group(8);
        this.userAgent = new UserAgent(matcher.group(9));

    }

    public String getIpAdd() {
        return ipAdd;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public int getCodeResponse() {
        return codeResponse;
    }

    public int getSizeData() {
        return sizeData;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    private String [] methodPathParser (String methodPathString){
        return methodPathString.split(" ");
    }

    private LocalDateTime localDateTime (String localDateTime){
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(TIME)
                .toFormatter(Locale.ENGLISH);
        return LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAdd='" + ipAdd + '\'' +
                ", propertyOne='" + propertyOne + '\'' +
                ", propertyTwo='" + propertyTwo + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", codeResponse=" + codeResponse +
                ", sizeData=" + sizeData +
                ", referer='" + referer + '\'' +
                ", userAgent=" + userAgent +
                '}';
    }
}
