/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Consulta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Liz
 */
public interface ConsultaDao extends GenericDao<Consulta, Long>  {

    public List<Consulta> obtenerConsultasPaciente(String username);
    
    public List<Consulta> obtenerConsultasDoctor(String username);
    
    public List<Consulta> obtenerConsultasFecha(Date fecha);
    
    public void guardar(Consulta consulta);
    
    public void eliminar(Consulta consulta);
}
