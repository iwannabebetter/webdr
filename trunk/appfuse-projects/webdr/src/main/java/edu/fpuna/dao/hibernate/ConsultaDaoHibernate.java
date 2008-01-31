package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
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
    
        log.debug("--> Busqueda Finalizada." + retorno.getFechaInicio());
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

    private String obtenerRol(User usuario) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
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
                                            Date fechaInicio, Date fechaFin) {
        String query = "from Consulta where cast(fecha as date) >= cast(? as date) and cast(fecha as date)<=cast(? as date)";
        //Date f = new Date(84, 5, 5);
        log.debug("FECHA: " + fechaInicio.toString() + " && " + fechaInicio.toString());
        Date[] fechas = {fechaInicio, fechaFin};
        return getHibernateTemplate().find(query, fechas);
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
}
