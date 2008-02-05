/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service;

import edu.fpuna.dao.HorarioAtencionDao;
import edu.fpuna.model.HorarioAtencion;
import java.util.List;

/**
 *
 * @author fmancia
 */
public interface HorarioAtencionManager extends GenericManager<HorarioAtencion, Long> {

    
    public void setHorarioAtencionDao(HorarioAtencionDao horarioAtencionDao);
    
    public void setDoctorManager(DoctorManager doctorManager);
    
    /**
     * {@inheritDoc}
     */
    public List<HorarioAtencion> getHorarioAtencion();

    /**
     * {@inheritDoc}
     */
    public List<HorarioAtencion> getHorarioAtencion(String usernameDoctor);

    /**
     * {@inheritDoc}
     */
    public HorarioAtencion getHorarioAtencion(Long id);
    
    /**
     * {@inheritDoc}
     */
    public HorarioAtencion guardar(HorarioAtencion especialidad);

    /**
     * {@inheritDoc}
     */
    public void remove(HorarioAtencion obj);
}
