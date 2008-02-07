/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service;

import edu.fpuna.dao.TurnoDao;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import java.util.List;

/**
 *
 * @author fmancia
 */
public interface TurnoManager extends GenericManager<Turno, Long> {

    
    public void setTurnoDao(TurnoDao TurnoDao);
    
    public void setHorarioAtencionManager(HorarioAtencionManager hmgr);
        
    /**
     * {@inheritDoc}
     */
    public List<Turno> getTurnos(HorarioAtencion h);

    /**
     * {@inheritDoc}
     */
    public Turno getTurno(Long id);
    
    /**
     * {@inheritDoc}
     */
    public Turno guardar(Turno especialidad);

    /**
     * {@inheritDoc}
     */
    public void remove(Turno obj);
}
