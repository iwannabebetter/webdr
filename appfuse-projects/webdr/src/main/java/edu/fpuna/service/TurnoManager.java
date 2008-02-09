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

    
    public void setTurnoDao(TurnoDao turnoDao);
    
    public void setHorarioAtencionManager(HorarioAtencionManager hmgr);
        
    /**
     * {@inheritDoc}
     */
    public List<Turno> getTurnos(HorarioAtencion h);

    /**
     * {@inheritDoc}
     */
    public Turno getTurno(Long id);
    
    
    public Turno guardarConHorario(Turno turno, HorarioAtencion ha);
    
    /**
     * {@inheritDoc}
     */
    public Turno guardar(Turno turno);

    /**
     * {@inheritDoc}
     */
    public void remove(Turno obj);
}
