package pathway.ui.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;
import org.jdesktop.beansbinding.Converter;




public class TimestampConverter extends Converter<Timestamp, String> {
    public TimestampConverter() {
        this(DateFormat.FULL, DateFormat.FULL, Locale.GERMANY);
    }


    public TimestampConverter(int dateFormat, int timeFormat) {
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
        this.locale = Locale.GERMANY;
    }


    public TimestampConverter(int dateFormat, int timeFormat, Locale locale) {
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
        this.locale = locale;
    }


    @Override
    public String convertForward(Timestamp value) {
        if( timeFormat < 0 )
            return DateFormat.getDateInstance(dateFormat, locale).format(value);

        return DateFormat.getDateTimeInstance(dateFormat, timeFormat, locale).format(value);
    }


    @Override
    public Timestamp convertReverse(String value) {
        try {
            return new Timestamp(DateFormat.getInstance().parse(value).getTime());
        }
        catch( ParseException e ) {
            return new Timestamp(Calendar.getInstance().getTimeInMillis());
        }
    }


    private int dateFormat;
    private int timeFormat;
    private Locale locale;
}
