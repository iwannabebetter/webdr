/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.TurnoDao;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import edu.fpuna.service.HorarioAtencionManager;
import edu.fpuna.service.TurnoManager;
import java.util.List;

/**
 *
 * @author fmancia
 */
public class TurnoManagerImpl 
    extends GenericManagerImpl<Turno, Long> implements TurnoManager {
    private HorarioAtencionManager horarioAtencionManager;
    private TurnoDao turnoDao;
    
    public TurnoManagerImpl(TurnoDao turnoDao) {
        super(turnoDao);
        this.turnoDao = turnoDao;
    }

    public void setHorarioAtencionManager(HorarioAtencionManager HorarioAtencionManager){
        this.horarioAtencionManager = HorarioAtencionManager;
    }
    
    public void setTurnoDao(TurnoDao turnoDao){
        this.turnoDao = turnoDao;
    }
    
    
    public List<Turno> getTurnos(HorarioAtencion h) {
        return turnoDao.getTurnos(h);
    }
    
    public Turno getTurno(Long id) {
        return turnoDao.getTurno(id);
    }

    public Turno guardar(Turno turno) {
        return turnoDao.guardar(turno);
    }
    
    public Turno guardarConHorario(Turno turno, HorarioAtencion ha) {
        turno.setHorario(ha);
        return turnoDao.guardar(turno);
    }

    public void remove(Turno obj) {
        turnoDao.eliminar(obj);
    }

}
