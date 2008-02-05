package edu.fpuna.webapp.action;

/**
 *
 * @author mrodas
 */
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;
import edu.fpuna.util.CurrencyConverter;
import com.opensymphony.xwork2.util.TypeConversionException;

public class DoubleConverter extends StrutsTypeConverter {
    private static Log log = LogFactory.getLog(CurrencyConverter.class);

    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (value[0] == null || value[0].trim().equals("")) {
            return null;
        }

        CurrencyConverter doubleConverter =new CurrencyConverter();
        try {
            log.debug("Clases: "+arg2);
            if (arg2 == Double.class)
                return doubleConverter.convert(arg2, value[0]);
            else
                return null;

        } catch (Exception pe) {
            pe.printStackTrace();
            throw new TypeConversionException(pe.getMessage());
        }
    }

    public String convertToString(Map ctx, Object data) {
        CurrencyConverter doubleConverter =new CurrencyConverter();
        log.debug("Clases: "+ data.getClass());
        if (data.getClass() == Double.class)
            return doubleConverter.convert(data.getClass(), data).toString();
        else
            return null;
    }
} 
