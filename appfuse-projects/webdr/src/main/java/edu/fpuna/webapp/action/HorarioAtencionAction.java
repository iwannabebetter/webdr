/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Doctor;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.HorarioAtencionManager;
import java.util.List;

/**
 *
 * @author Liz
 */
public class HorarioAtencionAction extends BaseAction {
    private HorarioAtencionManager horarioAtencionManager;
    private DoctorManager doctorManager;
    private List<HorarioAtencion> horariosDoctor;
    private Long id;
    private String doctorUsername;
    
    public void setHorarioAtencionManager(HorarioAtencionManager horarioAtencionManager) {
        this.horarioAtencionManager = horarioAtencionManager;
    }
    
    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }
    
    public List<HorarioAtencion> getHorariosDoctor() {
        return this.horariosDoctor;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setHorariosDoctor(List<HorarioAtencion> l) {
        this.horariosDoctor = l;
    }
    
    public String getDoctorUsername(){
        return doctorUsername;
    }
    
    public void setDoctorUsername(String doctorUsername){
        this.doctorUsername = doctorUsername;
    }
    
    public String listarHorarios() {
        horariosDoctor = horarioAtencionManager.getHorarioAtencion(doctorUsername);
        return SUCCESS;
    }
}
