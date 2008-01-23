/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Address;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ghuttemann
 */
public class DoctorDaoTest extends BaseDaoTestCase {
    private EspecialidadDao especialidadDao = null;
    private DoctorDao doctorDao = null;
    private final int CANT_DOC  = 10;
    
    public void setEspecialidadDao(EspecialidadDao especialidadDao) {
        this.especialidadDao = especialidadDao;
    }
    
    public void setDoctorDao(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }
    
    public void testObtenerPorEspecialidad() {
        Address address = new Address();
        address.setCity("Asunción");
        address.setPostalCode("123456");
        
        log.debug("Recuperando especialidades...");
        List<Especialidad> especialidades = especialidadDao.getAll();
        assertTrue(especialidades.size() == 2);
        log.debug(especialidades.size() + " especialidades recuperadas...");
        
        log.debug("Creando doctores...");
        for (int i=0; i < CANT_DOC; i++) {
            Doctor doctor = new Doctor();
            doctor.setUsername("user" + i);
            doctor.setEmail("user" + i + "@gmail.com");
            doctor.setFirstName("Fulano" + i);
            doctor.setLastName("Lalala");
            doctor.setAccountExpired(false);
            doctor.setAccountLocked(false);
            doctor.setCredentialsExpired(false);
            doctor.setPassword(".cambiar.");
            doctor.setAddress(address);
            doctor.setFechaNacimiento(new Date());
            doctor.setRegistro(1234);
            
            Especialidad esp;
            if (i % 2 == 0)
                esp = especialidades.get(0);
            else
                esp = especialidades.get(1);
            
            log.debug("Agregando especialidad \"" + esp + "\" al doctor " + i + "...");
            doctor.agregarEspecialidad(esp);
            
            log.debug("Guardando doctor " + i + "...");
            doctorDao.save(doctor);
            flush();
        }
        log.debug("Doctores creados y guardados...");
        
        Especialidad esp = especialidades.get(0);
        log.debug("Recuperando doctores con especialidad \"" + esp + "\"...");
        List<Doctor> doctores = doctorDao.obtenerPorEspecialidad(esp);
        assertTrue(doctores.size() > 0);

        log.debug(doctores.size() + " doctores recuperados con especialidad \"" + esp + "\"...");
        for (int i=0; i < doctores.size(); i++) {
            Doctor doc = doctores.get(i);
            log.debug("\t" + doc.getUsername());
        }
    }
}
