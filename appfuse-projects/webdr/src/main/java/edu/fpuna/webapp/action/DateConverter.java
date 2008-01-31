package edu.fpuna.webapp.action;

import java.text.ParseException;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;
import edu.fpuna.util.DateUtil;
import com.opensymphony.xwork2.util.TypeConversionException;

public class DateConverter extends StrutsTypeConverter {
    private static Log log = LogFactory.getLog(DateUtil.class);

    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (value[0] == null || value[0].trim().equals("")) {
            return null;
        }

        try {
            log.debug("Clases: "+arg2);
            if (arg2 == Timestamp.class)
                return DateUtil.convertStringToDateTime(value[0]);
            else if (arg2 == Date.class)
                return DateUtil.convertStringToDate(value[0]);
            else
                return null;

        } catch (ParseException pe) {
            pe.printStackTrace();
            throw new TypeConversionException(pe.getMessage());
        }
    }

    public String convertToString(Map ctx, Object data) {
        log.debug("Clases: "+ data.getClass());
        if (data.getClass() == Timestamp.class)
            return DateUtil.convertDateTimeToString((Date) data);
        else if (data.getClass() == Date.class)
            return DateUtil.convertDateToString((Date) data);
        else
            return null;
    }
} 
