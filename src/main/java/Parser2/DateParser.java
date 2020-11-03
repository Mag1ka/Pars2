package Parser2;

import org.joda.time.DateTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DateParser {
    private final HashMap<String, Integer> monthTrans;
    private final String today;
    private final String yesterday;

    public DateParser() throws IOException {
        List<String> month = Arrays.asList(Files.readString(Path.of("./month.txt")).split(" "));
        this.monthTrans = new HashMap<>();
        this.monthTrans.put(month.get(0), 1);
        this.monthTrans.put(month.get(1), 2);
        this.monthTrans.put(month.get(2), 3);
        this.monthTrans.put(month.get(3), 4);
        this.monthTrans.put(month.get(4), 5);
        this.monthTrans.put(month.get(5), 6);
        this.monthTrans.put(month.get(6), 7);
        this.monthTrans.put(month.get(7), 8);
        this.monthTrans.put(month.get(8), 9);
        this.monthTrans.put(month.get(9), 10);
        this.monthTrans.put(month.get(10), 11);
        this.monthTrans.put(month.get(11), 12);
        this.today = month.get(12);
        this.yesterday = month.get(13);



    }

    public DateTime parsDate(String str) {
        if (str.contains(this.today)) {
            return (new DateTime()).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        }

        if(str.contains(this.yesterday)){
            return (new DateTime()).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).minusDays(1).withMillisOfSecond(0);
        }
        //28 июл 20, 19:54
        List<String> split = Arrays.asList(str.split(" "));
        int dayOfMonth = parseInt(split.get(0));
        int month = monthTrans.get(split.get(1));
        int year = parseInt(split.get(2).replace(",",""));
        return (new DateTime()).withYear(year+2000).withMonthOfYear(month).withDayOfMonth(dayOfMonth).withMinuteOfHour(0).withSecondOfMinute(0).withHourOfDay(0).withMillisOfSecond(0);
    }
}

