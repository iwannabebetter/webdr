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
        Doctor[] doctores = new Doctor[CANT_DOC];
        for (int i=0; i < CANT_DOC; i++) {
            doctores[i] = new Doctor();
            doctores[i].setUsername("user" + i);
            doctores[i].setEmail("user" + i + "@gmail.com");
            doctores[i].setFirstName("Fulano" + i);
            doctores[i].setLastName("Lalala");
            doctores[i].setAccountExpired(false);
            doctores[i].setAccountLocked(false);
            doctores[i].setCredentialsExpired(false);
            doctores[i].setPassword(".cambiar.");
            doctores[i].setAddress(address);
            doctores[i].setFechaNacimiento(new Date());
            doctores[i].setRegistro(1234);
            
            Especialidad esp;
            if (i % 2 == 0)
                esp = especialidades.get(0);
            else
                esp = especialidades.get(1);
            
            log.debug("Agregando especialidad \"" + esp + "\" al doctor " + i + "...");
            doctores[i].agregarEspecialidad(esp);
            
            log.debug("Guardando doctor " + i + "...");
            doctorDao.save(doctores[i]);
            flush();
        }
        log.debug("Doctores creados y guardados...");
        
        Especialidad esp = especialidades.get(0);
        log.debug("Recuperando doctores con especialidad \"" + esp + "\"");
        List<Doctor> result = doctorDao.obtenerPorEspecialidad(esp);
        assertFalse(result.isEmpty());
        log.debug(result.size() + " doctores recuperados con especialidad \"" + esp + "\"...");
        for (Doctor doc : result)
            log.debug("\t" + doc.getUsername());
        
        log.debug("Pruebas terminadas...");
    }
}
