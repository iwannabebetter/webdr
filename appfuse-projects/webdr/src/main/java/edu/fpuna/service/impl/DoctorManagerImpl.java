/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.DoctorDao;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.EspecialidadManager;
import edu.fpuna.service.UserExistsException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.acegisecurity.userdetails.UsernameNotFoundException;
/**
 * Implementación de la interfaz Manager de Doctor.
 * @author ghuttemann
 */
public class DoctorManagerImpl 
    extends GenericManagerImpl<Doctor, Long> implements DoctorManager {
    private DaoAuthenticationProvider authenticationProvider;
    private EspecialidadManager especialidadManager;
    private DoctorDao doctorDao;
    
    public DoctorManagerImpl(DoctorDao doctorDao,DaoAuthenticationProvider authenticationProvider) {
        super(doctorDao);
        this.doctorDao = doctorDao;
        this.authenticationProvider = authenticationProvider;
    }
    
    public void setDoctorDao(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    public Doctor obtenerDoctorPorNombre(String username) throws UsernameNotFoundException {
        return doctorDao.obtenerPorNombre(username);
    }

    public Doctor guardarDoctor(Doctor doctor) throws UserExistsException {
        doctor.setAccountLocked(false);
        doctor.setAccountExpired(false);
        doctor.setEnabled(true);
        doctor.setCredentialsExpired(false);
        
        if (doctor.getVersion() == null) {
            // if new doctor, lowercase userId
            doctor.setUsername(doctor.getUsername().toLowerCase());
        }
        
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (authenticationProvider != null) {
            PasswordEncoder passwordEncoder = authenticationProvider.getPasswordEncoder();

            if (passwordEncoder != null) {
                // Check whether we have to encrypt (or re-encrypt) the password
                if (doctor.getVersion() == null) {
                    // New doctor, always encrypt
                    passwordChanged = true;
                } else {
                    // Existing doctor, check password in DB
                    String currentPassword = doctorDao.getUserPassword(doctor.getUsername());
                    if (currentPassword == null) {
                        passwordChanged = true;
                    } else {
                        if (!currentPassword.equals(doctor.getPassword())) {
                            passwordChanged = true;
                        }
                    }
                }

                // If password was changed (or new user), encrypt it
                if (passwordChanged) {
                    doctor.setPassword(passwordEncoder.encodePassword(doctor.getPassword(), null));
                }
            } else {
                log.warn("PasswordEncoder not set on AuthenticationProvider, skipping password encryption...");
            }
        } else {
            log.warn("AuthenticationProvider not set, skipping password encryption...");
        }
        
      //  try {
        return doctorDao.save(doctor);
    }

    public void setAuthenticationProvider(DaoAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
    

    public void setEspecialidadManager(EspecialidadManager especialidadManager) {
        this.especialidadManager = especialidadManager;
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
