/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.User;
import java.util.List;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import javax.persistence.Table;

/**
 * Implementación Hibernate de la interfaz DAO de Paciente.
 * @author ghuttemann
 */
public class PacienteDaoHibernate 
        extends GenericDaoHibernate<Paciente, Long> implements PacienteDao {

    public PacienteDaoHibernate() {
        super(Paciente.class);
    }
    
    public Paciente getPaciente(Long id) {
        String query = "from Paciente where id=?";
        List result = getHibernateTemplate().find(query, id);
        
        if (result.isEmpty())
            return null;
        
        return (Paciente) result.get(0);
    }
    
    public Paciente getPaciente(String username) {
        List result = super.getHibernateTemplate().find("from Paciente where username = ?", username);
        
        if (result.isEmpty())
            return null;
        
        return (Paciente) result.get(0);
    }
    
    public List<Paciente> getAll() {
        List result = super.getHibernateTemplate().find("from Paciente ");
        
        if (result.isEmpty())
            return null;
        
        return result;
    }

    public Paciente guardar(Paciente p) {
        return super.save(p);
    }

    public void eliminar(Paciente p) {
        super.remove(p.getId());
    }
    
    /** 
     * {@inheritDoc}
    */
    public String getUserPassword(String username) {
        SimpleJdbcTemplate jdbcTemplate =
                new SimpleJdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where username=?", String.class, username);

    }
}
