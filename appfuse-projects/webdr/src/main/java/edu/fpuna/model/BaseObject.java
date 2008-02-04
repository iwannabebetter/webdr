package edu.fpuna.model;

import edu.fpuna.Constants.FormatoFecha;
import java.io.Serializable;
import java.util.Calendar;


/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode().
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public abstract class BaseObject implements Serializable {    

    /**
     * Returns a multi-line String with key=value pairs.
     * @return a String representation of this class.
     */
    public abstract String toString();

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     * @return hashCode
     */
    public abstract int hashCode();
    
    /**
     * Convierte una fecha a su formato texto dependiendo
     * del tipo de la fecha.
     * @param fechaHora La fecha a ser convertida.
     * @return La representación texto de la fecha.
     */
    public String formatearFecha(java.util.Date fechaHora, FormatoFecha tipo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHora);
        String[] valores = {
            "" + cal.get(Calendar.DAY_OF_MONTH),
            "" + ((cal.get(Calendar.MONTH)) + 1),
            "" + cal.get(Calendar.YEAR),
            "" + cal.get(Calendar.HOUR_OF_DAY),
            "" + cal.get(Calendar.MINUTE)
        };
        
        // Normalizamos los valores
        for (int i=0; i < valores.length; i++)
            if (valores[i].length() == 1)
                valores[i] = "0" + valores[i];
        
        // Fecha en formato dd/MM/yyyy 
        String fecha = valores[0] + "/" +
                       valores[1] + "/" +
                       valores[2];
        
        // Hora en formato HH:mm
        String hora = valores[3] + ":" +
                      valores[4];
        
        // Retornamos la cadena según el tipo de fecha
        if (tipo == FormatoFecha.FECHAHORA)
            return fecha + " " + hora;
        else if (tipo == FormatoFecha.FECHA)
            return fecha;
        else if (tipo == FormatoFecha.HORA)
            return hora;
        else
            return null;
    }
}
