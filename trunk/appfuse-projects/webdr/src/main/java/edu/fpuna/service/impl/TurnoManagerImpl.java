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
    private HorarioAtencionManager HorarioAtencionManager;
    private TurnoDao TurnoDao;
    
    public TurnoManagerImpl(TurnoDao TurnoDao) {
        super(TurnoDao);
        this.TurnoDao= TurnoDao;
    }

    public void setHorarioAtencionManager(HorarioAtencionManager HorarioAtencionManager){
        this.HorarioAtencionManager = HorarioAtencionManager;
    }
    
    public void setTurnoDao(TurnoDao TurnoDao){
        this.TurnoDao = TurnoDao;
    }
    
    
    public List<Turno> getTurnos(HorarioAtencion h) {
        return TurnoDao.getTurnos(h);
    }
    
    public Turno getTurno(Long id) {
        return TurnoDao.getTurno(id);
    }

    public Turno guardar(Turno Turno) {
        return TurnoDao.guardar(Turno);
    }
    
    public Turno guardarConHorario(Turno turno, HorarioAtencion ha) {
        turno.setHorario(ha);
        return TurnoDao.guardar(turno);
    }

    public void remove(Turno obj) {
        TurnoDao.eliminar(obj);
    }

}
