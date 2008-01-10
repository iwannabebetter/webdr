/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Consulta;
import java.util.List;

/**
 *
 * @author Liz
 */
public interface ConsultaDao extends GenericDao<Consulta, Long>  {

    List<Consulta> obtenerConsultasPaciente(String string);
    
    List<Consulta> obtenerConsultasFecha();
    
    List<Consulta> obtenerConsultasDoctor(String user);
}
