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
    private HorarioAtencion horarioAtencion;
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
    
    public void setHorarioAtencion(HorarioAtencion horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }
    
    public HorarioAtencion getHorarioAtencion(){
        return this.horarioAtencion;
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
    
    public String listPorDoctor() {
        horariosDoctor = horarioAtencionManager.getHorarioAtencion(doctorUsername);
        return SUCCESS;
    }
    
    public String edit() {
        if (id != null){
            horarioAtencion = horarioAtencionManager.getHorarioAtencion(id);
            doctorUsername = horarioAtencion.getDoctor().getUsername();
        }
        else{
            horarioAtencion = new HorarioAtencion();
        }
        return SUCCESS;
    }
    
    public String delete(){
        log.debug("ANTES DE BORRAR HORARIO ATENCION nro = "+horarioAtencion.getId());
        horarioAtencionManager.remove(horarioAtencion.getId());
        saveMessage(getText("horarioAtencion.deleted"));
        log.debug("DESPUES DE BORRAR HORARIO ATENCION");
        return SUCCESS;
    }
    
    public String save() {
        
        if (cancel != null)
            return CANCEL;
        
        if (delete != null)
            return delete();
        
        boolean isNew = (horarioAtencion.getId() == null);
        log.debug("ANTES DE GUARDAR HORARIO ATENCION");
        
        horarioAtencion.setDoctor(doctorManager.obtenerDoctorPorNombre(doctorUsername));
        horarioAtencion = horarioAtencionManager.guardar(horarioAtencion);
        this.doctorUsername = horarioAtencion.getDoctor().getUsername();
        log.debug("DESPUES DE GUARDAR HORARIO ATENCION");
        
        if(!isNew){
            return INPUT;
        }else{
            return SUCCESS;
        }
    }

    
    
}
