/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import edu.fpuna.model.Turno;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public interface TurnoDao extends GenericDao<Turno, Long> {
    
    public Turno getTurno(Long id);
    
    public List<Turno> getTurnos(HorarioAtencion horarioAt);
    
    public Turno guardar(Turno t);
    
    public void eliminar(Turno t);
    

}
