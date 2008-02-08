/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Doctor;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.HorarioAtencionManager;
import edu.fpuna.service.TurnoManager;
import java.util.List;

/**
 *
 * @author Liz
 */
public class HorarioAtencionAction extends BaseAction {
    private HorarioAtencionManager horarioAtencionManager;
    private DoctorManager doctorManager;
    private TurnoManager turnoManager;
            
    private List<HorarioAtencion> horariosDoctor;
    private HorarioAtencion horarioAtencion;
    private Long id;
    private Long idTurno;
    private String doctorUsername;
    
    public void setHorarioAtencionManager(HorarioAtencionManager horarioAtencionManager) {
        this.horarioAtencionManager = horarioAtencionManager;
    }
    
    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }

    public void setTurnoManager(TurnoManager turnoManager) {
        this.turnoManager = turnoManager;
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

    public Long getIdTurno(){
        return idTurno;
    }
    
    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
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
        
        horarioAtencion = horarioAtencionManager.get(horarioAtencion.getId());
        this.doctorUsername = horarioAtencion.getDoctor().getUsername();
        horarioAtencionManager.remove(horarioAtencion.getId());
        saveMessage(getText("horarioAtencion.deleted"));
        horariosDoctor = horarioAtencionManager.getHorarioAtencion(doctorUsername);
        
        log.debug("El username Doctor es... = "+doctorUsername);
        log.debug("DESPUES DE BORRAR HORARIO ATENCION");
        return listPorDoctor();
    }
    
    public String save() {
        log.debug("El username Doctor es... = "+doctorUsername);
        if (cancel != null){
            horariosDoctor = horarioAtencionManager.getHorarioAtencion(doctorUsername);
            return CANCEL;
        }
        if (delete != null){
            return delete();
        }
        
        if(idTurno !=null){
            return borrarTurno();
        }
        
        boolean isNew = (horarioAtencion.getId() == null);
        if(isNew){
            saveMessage(getText("horarioAtencion.created"));
        }else{
            saveMessage(getText("horarioAtencion.updated"));
        }
        
        horarioAtencion.setDoctor(doctorManager.obtenerDoctorPorNombre(doctorUsername));
        //horarioAtencion.setTurnos(turnos);
        horarioAtencion = horarioAtencionManager.guardar(horarioAtencion);
        this.doctorUsername = horarioAtencion.getDoctor().getUsername();
        return listPorDoctor();
    }

    public String borrarTurno(){
        this.id = turnoManager.getTurno(idTurno).getHorario().getId();
        turnoManager.remove(idTurno);
        this.horarioAtencion = horarioAtencionManager.get(this.id);
        return "turno_borrado";
    }
    
}
