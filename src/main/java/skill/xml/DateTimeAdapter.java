package skill.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String text) {
        return LocalDateTime.parse(text, DATE_TIME_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime date) {
        return DATE_TIME_FORMATTER.format(date);
    }
}