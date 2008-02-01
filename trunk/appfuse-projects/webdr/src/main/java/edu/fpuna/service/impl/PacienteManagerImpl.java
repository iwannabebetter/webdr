package edu.fpuna.service.impl;

import edu.fpuna.Constants;
import edu.fpuna.dao.PacienteDao;
import edu.fpuna.dao.RoleDao;
import edu.fpuna.model.Address;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.Role;
import edu.fpuna.model.TipoSangre;
import edu.fpuna.service.PacienteManager;
import edu.fpuna.service.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.acegisecurity.providers.encoding.PasswordEncoder;


/**
 * @author fmancia
 */
public class PacienteManagerImpl extends GenericManagerImpl<Paciente, Long>
        implements PacienteManager {
 
    private PacienteDao dao;
    private RoleDao roleDao;
    private GenericManager<TipoSangre, Long> tipoSangreManager = null;
    private DaoAuthenticationProvider authenticationProvider;
    
    /**
     * Constructor
     * @param pacienteDao
     */
    public PacienteManagerImpl(PacienteDao pacienteDao, DaoAuthenticationProvider authenticationProvider) {
        super(pacienteDao);
        this.dao = pacienteDao;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Para que haga el dependency injection
     * @param dao
     */
    public void setPacienteDao(PacienteDao dao) {
        this.dao = dao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    
    public void setTipoSangreManager(GenericManager<TipoSangre, Long> tipoSangreManager) {
        this.tipoSangreManager = tipoSangreManager;
    }
    
    public Paciente getPaciente(Long id) {
        return dao.getPaciente(id);
    }
    
    public Paciente getPaciente(String username) {
        return dao.getPaciente(username);
    }
   
    public Paciente guardarPaciente(Paciente paciente){
        paciente.setAccountLocked(false);
        paciente.setAccountExpired(false);
        paciente.setEnabled(true);
        paciente.setCredentialsExpired(false);
        
        if (paciente.getVersion() == null) {
            // if new paciente, lowercase userId
            paciente.setUsername(paciente.getUsername().toLowerCase());
        }
        
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (authenticationProvider != null) {
            PasswordEncoder passwordEncoder = authenticationProvider.getPasswordEncoder();

            if (passwordEncoder != null) {
                // Check whether we have to encrypt (or re-encrypt) the password
                if (paciente.getVersion() == null) {
                    // New paciente, always encrypt
                    passwordChanged = true;
                } else {
                    // Existing paciente, check password in DB
                    String currentPassword = dao.getUserPassword(paciente.getUsername());
                    if (currentPassword == null) {
                        passwordChanged = true;
                    } else {
                        if (!currentPassword.equals(paciente.getPassword())) {
                            passwordChanged = true;
                        }
                    }
                }

                // If password was changed (or new user), encrypt it
                if (passwordChanged) {
                    paciente.setPassword(passwordEncoder.encodePassword(paciente.getPassword(), null));
                }
            } else {
                log.warn("PasswordEncoder not set on AuthenticationProvider, skipping password encryption...");
            }
        } else {
            log.warn("AuthenticationProvider not set, skipping password encryption...");
        }
        
      //  try {
            return dao.guardar(paciente);
       /** } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("paciente '" + paciente.getUsername() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("paciente '" + paciente.getUsername() + "' already exists!");
        }**/
    }

    public void setAuthenticationProvider(DaoAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
    
    public void eliminarPaciente(Paciente p) {
        dao.eliminar(p);
    }

    public boolean eliminarPaciente(Long id) {
        dao.remove(id);
        Paciente res = dao.get(id);
        
        if (res != null && res.getFirstName() != null)
            return false;
        
        return true;
    }
    
    public List<Paciente> obtenerTodos(){
        return dao.getAll();
    }
    
    /* [cpara 25-01-2008]
     * Por alguna razón, la definición del Bean de Paciente en el 
     * applicationContext-service.xml no funciona cuando agregamos nuevos 
     * propertys para hacer dependency injection del RoleDao y del TipoSangre Manager
     * 
     * Por esta razón, este método no funciona, sin embargo, lo dejamos aquí para 
     * revisarlo posteriormente. 
     * 
     */
    public Paciente pacienteAleatorio() {
        try {

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
            Date fechaNacimiento = (Date) formatter.parse("01/29/02");
            nuevoPaciente.setFechaNacimiento(fechaNacimiento);

            Role role = roleDao.getRoleByName(Constants.USER_ROLE);
            nuevoPaciente.addRole(role);
            /*<--- Datos del paciente */

            TipoSangre tiposangre = tipoSangreManager.get(-1L);
            nuevoPaciente.setTipoSangre(tiposangre);
            return nuevoPaciente;
        } catch (ParseException ex) {
            Logger.getLogger(PacienteManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


}
