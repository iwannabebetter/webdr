/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.DoctorDao;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.UserExistsException;
import java.util.List;
import org.acegisecurity.userdetails.UsernameNotFoundException;

/**
 * Implementación de la interfaz Manager de Doctor.
 * @author ghuttemann
 */
public class DoctorManagerImpl 
    extends GenericManagerImpl<Doctor, Long> implements DoctorManager {
    
    private DoctorDao doctorDao;

    public DoctorManagerImpl(DoctorDao doctorDao) {
        super(doctorDao);
        this.doctorDao = doctorDao;
    }
    
    public void setDoctorDao(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    public Doctor obtenerDoctorPorNombre(String username) throws UsernameNotFoundException {
        return doctorDao.obtenerPorNombre(username);
    }

    public Doctor guardarDoctor(Doctor doctor) throws UserExistsException {
        /* Faltan validaciones idem User */
        return doctorDao.save(doctor);
    }

    public void eliminarDoctor(Doctor doctor) {
        doctorDao.remove(doctor.getId());
    }

    public List<Doctor> obtenerDoctores() {
        return doctorDao.getAll();
    }

    public List<Doctor> obtenerDoctoresPorEspecialidad(Especialidad especialidad) {
        return doctorDao.obtenerPorEspecialidad(especialidad);
    }
}
