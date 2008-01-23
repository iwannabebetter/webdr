/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.Constants;
import edu.fpuna.model.Address;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.Role;
import edu.fpuna.model.TipoSangre;
import edu.fpuna.service.GenericManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Fernando
 */
public class PacienteDaoTest extends BaseDaoTestCase {
    
    // Objeto DAO de prueba
    private PacienteDao pacienteDao = null;
    private RoleDao roleDao = null;
    private GenericManager<TipoSangre, Long> tipoSangreManager = null;
    
    public void setPacienteDao(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
    }
    
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setTipoSangreManager(GenericManager<TipoSangre, Long> tipoSangreManager) {
        this.tipoSangreManager = tipoSangreManager;
    }
    
    public void testObtenerPaciente() throws Exception {
         log.debug("Iniciando Obtener Paciente...");
         Paciente res = pacienteDao.getPaciente(-1L);
         assertTrue(res != null && res.getId() == -1L );
         log.debug("Fin Obtener Paciente...");
    }
    
    /*
     * Test nº1. 
     */
    public void testGuardarPaciente() throws Exception {
        log.debug("Iniciando Guardar paciente...");

        /* Datos de Usuario */
        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setUsername("fmancia");
        nuevoPaciente.setPassword("fmancia2");
        nuevoPaciente.setFirstName("Fernando");
        nuevoPaciente.setLastName("Mancía");
        Address address = new Address();
        address.setCity("Asuncion");
        address.setProvince("Central");
        address.setCountry("PY");
        address.setPostalCode("80210");
        nuevoPaciente.setAddress(address);
        nuevoPaciente.setEmail("fmancia@appfuse.org");
        nuevoPaciente.setWebsite("http://fmancia.raibledesigns.com");
        nuevoPaciente.setAccountExpired(false);
        nuevoPaciente.setAccountLocked(false);
        nuevoPaciente.setCredentialsExpired(false);
        nuevoPaciente.setEnabled(true);
        /*<--- Datos del usuario */
        
        /*---> Datos propios del Paciente */
        nuevoPaciente.setCedula(2059843);
        
        Date fechaIngreso = new Date(System.currentTimeMillis());
        nuevoPaciente.setFechaIngreso(fechaIngreso);
        
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date fechaNacimiento = (Date)formatter.parse("01/29/02");
        nuevoPaciente.setFechaNacimiento(fechaNacimiento);
            
        log.debug("Agregando el Rol "+Constants.USER_ROLE+"...");        
        Role role = roleDao.getRoleByName(Constants.USER_ROLE);
        assertNotNull(role.getId());
        log.debug("---> role = "+role.getName());
        nuevoPaciente.addRole(role);
        /*<--- Datos del paciente */
        
        log.debug("Agregando el Tipo de Sangre ");
        TipoSangre tiposangre = tipoSangreManager.get(-1L);
        
        log.debug("---> tiposangre = "+tiposangre.getNombre());
        nuevoPaciente.setTipoSangre(tiposangre);
        
        log.debug("Guardando el Paciente...");
        Paciente p = pacienteDao.guardar(nuevoPaciente);
        flush();
        
        log.debug("Paciente Guardado...");
        
        assertNotNull(p.getId());
        Paciente retornado = pacienteDao.get(p.getId());
        assertEquals("fmancia2", retornado.getPassword());

        log.debug("Guardar Confirmado...");
    }
    
    /*
     * Test nº2. Prueba de busqueda de Consulta por Paciente
     
    public void testObtenerConsultasPaciente() throws Exception {
        log.debug("Testing(2) ObtenerConsultasPaciente...");

        List<Consulta> consultas = consultaDao.obtenerConsultasPaciente("nuevoPaciente");
       
        assertTrue(consultas.isEmpty() != true);
        log.debug("Cantidad de consultas del Paciente: " + consultas.size());
        log.debug("Testing ObtenerConsultasPaciente ha Finalizado.");
    }

     * Test nº3. Prueba de busqueda de Consulta por Doctor
     
    public void testObtenerConsultasDoctor() throws Exception { 
        log.debug("Testing(3) ObtenerConsultasDoctor...");
        
        List<Consulta> consultas = consultaDao.obtenerConsultasDoctor("admin");
        
        assertTrue(consultas.isEmpty() != true);
        log.debug("Cantidad de consultas del Doctor: " + consultas.size());
        log.debug("Testing ObtenerConsultasDoctor ha Finalizado.");
    }

    public void testEliminarConsulta() throws Exception { 
        log.debug("Testing(5) EliminarConsulta...");
        
        consultaDao.remove((long)-1);
        
        log.debug("Testing EliminarConsulta ha Finalizado.");
    }**/
}
