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
     * Metodo para obtener las Consultas por idConsulta.
     * @params username id de consulta.
     * @return List<Consultas> Lista de Consultas del Paciente.
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
    
        log.debug("--> Busqueda Finalizada." + retorno.getFecha().toString());
        return retorno;
    }
    
    /**
     * Metodo para obtener las Consultas de un Pacinete.
     * @params username Nombre del Paciente.
     * @return List<Consultas> Lista de Consultas del Paciente.
     */
    public List<Consulta> obtenerConsultasPaciente(String username) {
        log.debug("--> Buscando Consultas por Paciente: " + username + "...");
        
        long paciente_id = this.obtenerIdUsuario(username);
        String query = "from Consulta where paciente_id=?";
        List result = super.getHibernateTemplate().find(query, paciente_id);

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }
    
    /**
     * Metodo para obtener las Consultas de un Doctor.
     * @param username Nombre del Doctor.
     * @return List<Consultas> Lista de Consultas del Doctor.
     */
    public List<Consulta> obtenerConsultasDoctor(String username) {
        log.debug("--> Buscando Consultas por Doctor: " + username + "...");
        
        Long doctor_id = this.obtenerIdUsuario(username);
        String query = "from Consulta where doctor_id=?";
        
        log.debug("--> Busqueda Finalizada.");
        
        return getHibernateTemplate().find(query, doctor_id);
    }
    
    private Long obtenerIdUsuario(String username) {
        log.debug("EMPEZAMOS LA CONSULTA SOBRE APPUSER.....");
        
        String query = "from User where username=?";
        List<User> list = getHibernateTemplate().find(query, username);
        
        log.debug("PASAMOS LA CONSULTA SOBRE APPUSER.....");
        
        Long retorno = 0L;
        if (list.isEmpty())
            log.debug("# " + username + " NO EXISTE EN APPUSER.");
        else
            retorno = list.get(0).getId();
        
        return retorno;
    }

    /**
     * Metodo para obtener las Consultas en una fecha dada.
     * @param fecha fecha a consultar.
     * @return List<Consulta> lista de consulta encontradas.
     */
    public List<Consulta> obtenerConsultasFecha(Date fechaInicio, Date fechaFin) {
        String query = "from Consulta where fecha>=? and fecha<=?";
        //Date f = new Date(84, 5, 5);
        log.debug("FECHA: " + fechaInicio.toString() + " && " + fechaInicio.toString());
        Date[] fechas = {fechaInicio, fechaFin};
        return getHibernateTemplate().find(query, fechas);
    }
    
    /**
     * Metodo para eliminar una Consulta.
     * @param consulta a eliminar.
     */
    public void eliminar(Consulta consulta) {
        super.remove(consulta.getId());
    }

    /**
     * Metodo para Guardar una Consulta
     * @param consulta consulta a guardar
     */
    public void guardar(Consulta consulta) {
        super.save(consulta);
    }
}
