/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.dao.EspecialidadDao;
import edu.fpuna.model.Address;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.DoctorManager;
import java.sql.Date;
import java.util.List;

/**
 * Clase de prueba del Action de Doctor.
 * @author ghuttemann
 */
public class DoctorActionTest extends BaseActionTestCase {
    
    private DoctorAction action;

    @Override
    protected void onSetUpInTransaction() throws Exception {
        log.debug("Realizando SetUp...");
        super.onSetUpInTransaction();
        
        log.debug("Recuperando DoctorManager...");
        DoctorManager manager = (DoctorManager) applicationContext.getBean("doctorManager");
        
        action = new DoctorAction();
        action.setDoctorManager(manager);
        
        // Agregar un doctor de prueba
        log.debug("Creando Doctor...");
        Doctor doctor = crearDoctor();
        
        log.debug("Guardando Doctor...");
        manager.guardarDoctor(doctor);
    }
    
    public void testList() throws Exception {
        log.debug("Probando lista de Doctores...");
        assertEquals(action.list(), DoctorAction.SUCCESS);
        
        log.debug("Probando cantidad de Doctores...");
        assertTrue(action.getDoctores().size() >= 1);
    }
    
    /*
     * Metodo para crear un doctor
     */
    private Doctor crearDoctor() {
        Address address = new Address();
        address.setCity("Asunción");
        address.setPostalCode("123456");
        
        Doctor doctor = new Doctor();
        doctor.setUsername("chapatin");
        doctor.setEmail("chapatin@gmail.com");
        doctor.setFirstName("Roberto");
        doctor.setLastName("Gómez Bolaños");
        doctor.setAccountExpired(false);
        doctor.setAccountLocked(false);
        doctor.setCredentialsExpired(false);
        doctor.setPassword(".cambiar.");
        doctor.setAddress(address);
        doctor.setFechaNacimiento(new Date(System.currentTimeMillis()));
        doctor.setRegistro(12340000);
        
        Especialidad esp = obtenerEspecialidad();
        doctor.agregarEspecialidad(esp);
        
        return doctor;
    }

    private Especialidad obtenerEspecialidad() {
        log.debug("Recuperando EspecialidadDao...");
        EspecialidadDao espDao = (EspecialidadDao) applicationContext.getBean("especialidadDao");
        
        log.debug("Recuperando especialidades...");
        List result = espDao.getAll();
        assertFalse(result.isEmpty());
        
        return (Especialidad) result.get(0);
    }
}
