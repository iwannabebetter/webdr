/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.Constants;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.EspecialidadManager;
import java.util.List;

/**
 * Action de la clase Doctor.
 * @author ghuttemann
 */
public class DoctorAction extends EditAccessAction {

    private DoctorManager doctorManager;
    private EspecialidadManager especialidadManager;
    private List<Doctor> doctores;
    private String especialidad;
    private Doctor doctor;
    private Long id;
    
    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }
    
    public void setEspecialidadManager(EspecialidadManager especialidadManager) {
        this.especialidadManager = especialidadManager;
    }
    
    public List<Doctor> getDoctores() {
        return doctores;
    }
    
    public String list() {
        if (especialidad != null)
            doctores = doctorManager.obtenerDoctoresPorEspecialidad(especialidad);
        else
            doctores = doctorManager.obtenerDoctores();
        
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
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String delete() {
        doctorManager.eliminarDoctor(doctor);
        saveMessage(getText("doctor.deleted"));
        return SUCCESS;
    }

    public String edit() {
        if (id != null){
            doctor = doctorManager.get(id);
            doctor.setConfirmPassword(doctor.getPassword());
        }
        else {
            doctor = new Doctor();
        }
        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null)
            return CANCEL;
        
        if (delete != null)
            return delete();
        
        boolean isNew = (doctor.getId() == null);

        //Roles del doctor
        doctor.getRoles().clear();
        doctor.addRole(roleManager.getRole(Constants.ID_USER_ROLE));
        doctor.addRole(roleManager.getRole(Constants.ID_DOCTOR_ROLE));
        

        //Especialidades del doctor
        doctor.getEspecialidades().clear();
        String[] docEspecialidades = getRequest().getParameterValues("doctorEspecialidad");
        for (int i = 0; docEspecialidades != null && i < docEspecialidades.length; i++) {
            doctor.agregarEspecialidad(especialidadManager.getEspecialidad(docEspecialidades[i]));
        }
        /**int n = docEspecialidades != null ? docEspecialidades.length : 0;
        log.debug("Cantidad de Especialidades = *" + getRequest().getParameter("doctor.especialidades")  + "*");
        
        Enumeration paramet = getRequest().getParameterNames();
        while( paramet.hasMoreElements() ) {
            log.debug("__PARAMETRO__ = " + paramet.nextElement());
        }**/
        
        doctor = doctorManager.guardarDoctor(doctor);
        String key = (isNew) ? "doctor.added" : "doctor.updated";
        saveMessage(getText(key));
        
        if (!isNew) {
            doctor.setConfirmPassword(doctor.getPassword());
            return INPUT;
        }
        else {
            return SUCCESS;
        }
    }
}
