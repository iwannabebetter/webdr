/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import edu.fpuna.dao.RoleDao;
import edu.fpuna.model.Address;
import edu.fpuna.model.Paciente;
import edu.fpuna.service.PacienteManager;
import edu.fpuna.model.Role;
import edu.fpuna.model.TipoSangre;
import edu.fpuna.service.GenericManager;
import edu.fpuna.service.impl.PacienteManagerImpl;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Clase de prueba del Action de Paciente.
 * @author Cristhian Parra
 */
public class PacienteActionTest extends BaseActionTestCase {

    private PacienteAction action;
    private RoleDao roleDao;
    private GenericManager<TipoSangre, Long> tipoSangreManager;

    protected void onSetUpBeforeTransaction() throws Exception {
        log.debug("__SETUP__:Seteo antes de la transaccion");
        super.onSetUpBeforeTransaction();
        
        log.debug("__SETUP__:Recuperando el Manager");
        PacienteManager pacienteManager = (PacienteManager) applicationContext.getBean("pacienteManager");
        this.roleDao = (RoleDao) applicationContext.getBean("roleDao");
        this.tipoSangreManager = (GenericManager<TipoSangre,Long>) applicationContext.getBean("tipoSangreManager");
        
        log.debug("__SETUP__:Estableciendo el Manager");
        action = new PacienteAction();
        action.setPacienteManager(pacienteManager);
        action.setTipoSangreManager(this.tipoSangreManager);
    }
    
    public void testSearch() throws Exception {
        log.debug("__TESTSEARCH__:Probando el listado de pacientes");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        
        log.debug("__TESTSEARCH__:Recuperando pacientes ");
        assertTrue(action.getPacientes().size() != 0);
        log.debug("__TESTSEARCH__:Fin Recuperando pacientes ");
        
        action.guardar(action.getPacientes().get(0));
        log.debug("__TESTSEARCH__:Guardar paciente ");
    }
    
    public void testEdit() throws Exception {
        log.debug("__TESTEDIT__:Testing edit...");
        action.setId(-1L);
        assertNull(action.getPaciente());
        log.debug("__TESTEDIT__:Testing antes de edit()");
        assertEquals("success", action.edit());
        log.debug("__TESTEDIT__:Testing despues de edit()");
        assertNotNull(action.getPaciente());
        assertFalse(action.hasActionErrors());
        log.debug("__TESTEDIT__:Testing Edit Finalizado... ");
    }

    public void testSave() throws Exception {
        log.debug("__TESTSAVE__:Testing Save... ");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        
        log.debug("__TESTSAVE__:Llamamos al action edit para obtener el paciente -1... ");
        action.setId(-1L);
        assertEquals("success", action.edit());
        assertNotNull(action.getPaciente());
        
        // update last name and save
        
        log.debug("__TESTSAVE__:Modificamos algunos campos, y luego hacemos save... ");
        action.getPaciente().setLastName("Updated Last Name");
        assertEquals("input", action.save());
        
        log.debug("__TESTSAVE__:Paciente saved. Verificaciones...");
        assertEquals("Updated Last Name", action.getPaciente().getLastName());
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());
        assertNotNull(request.getSession().getAttribute("messages"));
        log.debug("__TESTSAVE__:Testing Save Finalizado... ");
    }

    public void testRemove() throws Exception {
        log.debug("__TESTREMOVE__:Testing Remove... ");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        
        // creamos un nuevo paciente para luego borrarlo
        log.debug("__TESTREMOVE__:Creamos un nuevo Paciente para luego guardarlo y borrarlo... ");
        Paciente paciente = this.pacienteAleatorio();
        action.setId(paciente.getId());
        action.setPaciente(paciente);        
        action.save();
        
        log.debug("__TESTREMOVE__:Nuevo Paciente Guardado... ");
        
        log.debug("__TESTREMOVE__:Borrando Nuevo Paciente Guardado... ");
        action.setDelete("");
        assertEquals("success", action.delete());
        assertNotNull(request.getSession().getAttribute("messages"));
        log.debug("__TESTREMOVE__:Test de Remove finalizado... ");
    }
    
    public void testNuevoPaciente() throws Exception {
        log.debug("__TESTNUEVO__:Agregando un nuevo Paciente");        
        Paciente paciente = this.pacienteAleatorio();
        log.debug("__TESTNUEVO__:Paciente seteado");
        action.setPaciente(paciente);
        action.save();
        log.debug("__TESTNUEVO__:Paciente guardado");
    }
    
    private Paciente pacienteAleatorio() {
        try {
            Paciente nuevoPaciente = new Paciente();
            
            Random r = new Random();
            byte [] bytes = new byte[10];
            r.nextBytes(bytes);
            String username = bytes.toString();
            
            nuevoPaciente.setUsername(username);
            nuevoPaciente.setPassword(username+"_PASSWORD");
            nuevoPaciente.setFirstName(username+"_NAME");
            nuevoPaciente.setLastName(username+"_LASTNAME");
            Address address = new Address();
            address.setCity("Asuncion");
            address.setProvince("Central");
            address.setCountry("PY");
            address.setPostalCode("80210");
            nuevoPaciente.setAddress(address);
            nuevoPaciente.setEmail(username+"@appfuse.org");
            nuevoPaciente.setWebsite("http://"+username+".raibledesigns.com");
            nuevoPaciente.setAccountExpired(false);
            nuevoPaciente.setAccountLocked(false);
            nuevoPaciente.setCredentialsExpired(false);
            nuevoPaciente.setEnabled(true);
            /*<--- Datos del usuario */

            /*---> Datos propios del Paciente */
            nuevoPaciente.setCedula(r.nextInt(9999999));

            Date fechaIngreso = new Date(System.currentTimeMillis());
            nuevoPaciente.setFechaIngreso(fechaIngreso);

            nuevoPaciente.setFechaNacimiento(new Date(System.currentTimeMillis()));

            Role role = roleDao.getRoleByName("ROLE_PACIENTE");
            nuevoPaciente.addRole(role);
            /*<--- Datos del paciente */

            TipoSangre tiposangre = tipoSangreManager.get(-1L);
            nuevoPaciente.setTipoSangre(tiposangre);
            return nuevoPaciente;
            
        }
        catch (Exception ex) {
            Logger.getLogger(PacienteManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
