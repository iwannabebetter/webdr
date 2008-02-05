/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.HorarioAtencionDao;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.HorarioAtencionManager;
import java.util.List;

/**
 *
 * @author fmancia
 */
public class HorarioAtencionManagerImpl 
    extends GenericManagerImpl<HorarioAtencion, Long> implements HorarioAtencionManager {
    private DoctorManager doctorManager;
    private HorarioAtencionDao horarioAtencionDao;
    
    public HorarioAtencionManagerImpl(HorarioAtencionDao horarioAtencionDao) {
        super(horarioAtencionDao);
        this.horarioAtencionDao= horarioAtencionDao;
    }

    public void setDoctorManager(DoctorManager doctorManager){
        this.doctorManager = doctorManager;
    }
    
    public void setHorarioAtencionDao(HorarioAtencionDao horarioAtencionDao){
        this.horarioAtencionDao = horarioAtencionDao;
    }
    
    
    public List<HorarioAtencion> getHorarioAtencion() {
        return horarioAtencionDao.getAll();
    }

    
    public List<HorarioAtencion> getHorarioAtencion(String usernameDoctor) {
        return horarioAtencionDao.getHorarioAtencion(usernameDoctor);
    }

    
    public HorarioAtencion getHorarioAtencion(Long id) {
        return horarioAtencionDao.getHorarioAtencion(id);
    }

    
    public HorarioAtencion guardar(HorarioAtencion horarioAtencion) {
        return horarioAtencionDao.guardar(horarioAtencion);
    }

    public void remove(HorarioAtencion obj) {
        horarioAtencionDao.eliminar(obj);
    }
    

}
