package edu.fpuna.dao.hibernate;

import edu.fpuna.Constants;
import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.Role;
import edu.fpuna.model.User;
import java.util.Date;
import java.util.List;

/**
 * Implementación Hibernate del DAO correspondiente al manejo de Consulta
 * @author mrodas
 */
public class ConsultaDaoHibernate 
        extends GenericDaoHibernate<Consulta, Long> implements ConsultaDao {

    /**
     * Constructor
     */
    public ConsultaDaoHibernate() {
        super(Consulta.class);
    }

    /**
     * {@inheritDoc}
     */
    public Consulta obtenerConsultaId(Long id) {
        log.debug("--> Buscando Consulta por Id: " + id+" ...");
       
        String query = "from Consulta where id=?";
        List result = super.getHibernateTemplate().find(query, id);
        
        log.debug("--> Busqueda Finalizada.");
        
        Consulta retorno = null;
        if (result.size() > 1)
            log.debug("##ERROR## Consulta duplicada.");
        else
            retorno = (Consulta) result.get(0);
    
        log.debug("--> Busqueda Finalizada." + retorno.getFecha());
        return retorno;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Consulta> obtenerConsultasPaciente(String username) {
        log.debug("--> Buscando Consultas por Paciente: " + username + "...");
        
        User paciente = this.obtenerUsuario(username);
        String query = "from Consulta where paciente.id=?";
        List<Consulta> result = super.getHibernateTemplate()
                                     .find(query, paciente.getId());

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Consulta> obtenerConsultasDoctor(String username) {
        log.debug("--> Buscando Consultas por Doctor: " + username + "...");
        
        User doctor = this.obtenerUsuario(username);
        String query = "from Consulta where doctor.id=?";
        List<Consulta> result = super.getHibernateTemplate()
                                     .find(query, doctor.getId());
        
        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    /*
     * Dado un usuario, revisa los roles que tiene
     * y retorna el rol si es Doctor o Paciente.
     * En caso de no encontrar uno de estos roles,
     * se retorna null.
     * Se asume que no existen simultáneamente los 
     * dos roles mencionados más arriba.
     */
    private String obtenerRol(User usuario) {
        String dRole = Constants.DOCTOR_ROLE;
        String pRole = Constants.PACIENTE_ROLE;
        
        for (Role role : usuario.getRoles()) {
            String rName = role.getName();
            if (rName.equals(dRole) || rName.equals(pRole))
                return rName;
        }
        
        return null;
    }
    
    /*
     * Obtiene un usuario por el nombre de usuario
     */
    private User obtenerUsuario(String username) {
        log.debug("EMPEZAMOS LA CONSULTA SOBRE APPUSER.....");
        
        String query = "from User where username=?";
        List<User> list = getHibernateTemplate().find(query, username);
        
        log.debug("PASAMOS LA CONSULTA SOBRE APPUSER.....");
        
        if (list.isEmpty()) {
            log.fatal("# " + username + " NO EXISTE EN APPUSER.");
            return null;
        }
        else {
            log.debug("# " + username + " recuperado...");
            return list.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Consulta> obtenerConsultasFecha(String username, 
                            Date fechaInicio, Date fechaFin) throws Exception {
        
        log.debug("__INICIO__ :: ConsultaDaoHibernate.obtenerConsultasFecha()");
        
        User usuario = this.obtenerUsuario(username);
        log.debug("\tUsuario recuperado: '" + usuario.getUsername() + "'");
        
        String rol   = obtenerRol(usuario);
        log.debug("\tRol recuperado: '" + rol + "'");
        
        String query = "from Consulta ";
        
        /* Construimos la consulta según el tipo de usuario */
        if (rol == null) {
            String mensaje = "El argumento 'usuario' debe ser " +
                             "una instancia de '" + Doctor.class + 
                             "' o '" + Paciente.class + "'";
            
            throw new Exception(mensaje);
        }
        else if (rol.equals(Constants.DOCTOR_ROLE))
            query += "where doctor.id=? ";
        else if (rol.equals(Constants.PACIENTE_ROLE))
            query += "where paciente.id=? ";
        
        log.debug("\tFechas: '" + fechaInicio.toString() + "' && '" + fechaInicio.toString() + "'");
        
        /* Si las dos fechas son nulas, se recuperan todas las consultas */
        if (fechaInicio == null && fechaFin == null) {
            log.debug("\tQuery: '" + query + "'");
            return getHibernateTemplate().find(query, usuario.getId());
        }
            
        /* En caso contrario hay un rango válido */
        query += "and cast(? as date) <= cast(fecha as date) and " +
                 "cast(? as date) >= cast(fecha as date)";
        
        /* Construimos el rango de fechas */
        Object[] args = {usuario.getId(), null, null};
        if (fechaInicio == null) {
            args[1] = fechaFin;
            args[2] = fechaFin;
        }
        else if (fechaFin == null) {
            args[1] = fechaInicio;
            args[2] = fechaInicio;
        }
        else {
            args[1] = fechaInicio;
            args[2] = fechaFin;
        }
        
        log.debug("\tQuery: '" + query + "'");
        log.debug("__FIN__ :: ConsultaDaoHibernate.obtenerConsultasFecha()");
        return getHibernateTemplate().find(query, args);
    }
    
    /**
     * {@inheritDoc}
     */
    public Consulta guardar(Consulta consulta) {
        log.debug("user's id: " + consulta.getId());
        super.getHibernateTemplate().saveOrUpdate(consulta);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        //getHibernateTemplate().flush();
        return consulta;
    }

    /**
     * Overridden simply to call the saveUser method. This is happenening 
     * because saveConsulta flushes the session and saveObject of BaseDaoHibernate 
     * does not.
     *
     * @param consulta la consulta a guardar
     * @return la consulta modificada (con un conjunto de la clave primaria si ellas son nuevas)
     */
    @Override
    public Consulta save(Consulta consulta) {
        return this.guardar(consulta);
    }

    /**
     * {@inheritDoc}
     */
    public void eliminar(Consulta consulta) {
        super.remove(consulta.getId());
    }

    @Override
    public List<Consulta> getAll() {
        String query = "from Consulta order by fecha desc";
        List<Consulta> result = super.getHibernateTemplate().find(query);
        return result;
    }
}
