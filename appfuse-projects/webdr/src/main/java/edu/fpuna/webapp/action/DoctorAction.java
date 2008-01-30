/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.DoctorManager;
import java.util.List;

/**
 * Action de la clase Doctor.
 * @author ghuttemann
 */
public class DoctorAction extends BaseAction {

    private DoctorManager doctorManager;
    private List<Doctor> doctores;
    private Doctor doctor;
    private Long id;
    
    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }
    
    public List<Doctor> getDoctores() {
        return doctores;
    }
    
    public String list() {
        doctores = doctorManager.obtenerDoctores();
        return SUCCESS;
    }
    
    public String list(Especialidad especialidad) {
        doctores = doctorManager.obtenerDoctoresPorEspecialidad(especialidad);
        return SUCCESS;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String delete() {
        doctorManager.eliminarDoctor(doctor);
        saveMessage(getText("doctor.deleted"));
        return SUCCESS;
    }

    public String edit() {
        if (id != null)
            doctor = doctorManager.get(id);
        else
            doctor = new Doctor();

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null)
            return CANCEL;
        
        if (delete != null)
            return delete();
        
        boolean isNew = (doctor.getId() == null);
        doctor = doctorManager.guardarDoctor(doctor);
        String key = (isNew) ? "doctor.added" : "doctor.updated";
        saveMessage(getText(key));
        
        if (!isNew)
            return INPUT;
        else
            return SUCCESS;
    }
}
