/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;
import com.opensymphony.xwork2.util.TypeConversionException;
import edu.fpuna.model.DiaDeSemana;

/**
 *
 * @author Liz
 */
public class DiaDeSemanaConverter extends StrutsTypeConverter {
    private static Log log = LogFactory.getLog(DiaDeSemanaConverter.class);

    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (value[0] == null || value[0].trim().equals("")) {
            return null;
        }

        try {
            log.debug("Clases: "+arg2);
            if (arg2 == DiaDeSemana.class){
                String dia = value[0];
                if(dia.trim().equalsIgnoreCase("domingo")){
                    return DiaDeSemana.DOMINGO;
                }
                if(dia.trim().equalsIgnoreCase("lunes")){
                    return DiaDeSemana.LUNES;
                }
                if(dia.trim().equalsIgnoreCase("martes")){
                    return DiaDeSemana.MARTES;
                }
                if(dia.trim().equalsIgnoreCase("miercoles")){
                    return DiaDeSemana.MIERCOLES;
                }
                if(dia.trim().equalsIgnoreCase("jueves")){
                    return DiaDeSemana.JUEVES;
                }
                if(dia.trim().equalsIgnoreCase("viernes")){
                    return DiaDeSemana.VIERNES;
                }
                if(dia.trim().equalsIgnoreCase("sabado")){
                    return DiaDeSemana.SABADO;
                }
                return null;
            }
            return null;
            
        } catch (Exception pe) {
            pe.printStackTrace();
            throw new TypeConversionException(pe.getMessage());
        }
    }

    public String convertToString(Map ctx, Object data) {
        log.debug("Clases: "+ data.getClass());
        if (data.getClass() == DiaDeSemana.class)
            return ((DiaDeSemana)data).toString();
        else
            return null;
    }

}
