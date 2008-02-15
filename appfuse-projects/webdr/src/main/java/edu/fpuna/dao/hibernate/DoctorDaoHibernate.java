/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.DoctorDao;
import edu.fpuna.model.DiaDeSemana;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.User;
import java.util.List;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import javax.persistence.Table;
import org.acegisecurity.userdetails.UsernameNotFoundException;

/**
 * Implementación Hibernate del DAO correspondiente al manejo de Doctor
 * @author ghuttemann
 */
public class DoctorDaoHibernate 
    extends GenericDaoHibernate<Doctor, Long> implements DoctorDao {
    
    public DoctorDaoHibernate() {
        super(Doctor.class);
    }
    
    public Doctor obtenerPorNombre(String username) throws UsernameNotFoundException {
        String query = "from Doctor doc where doc.username=?";
        List result = super.getHibernateTemplate().find(query, username);
        
        if (result == null || result.isEmpty())
            throw new UsernameNotFoundException("Doctor: '" + username + "' no encontrado...");
        else
            return (Doctor) result.get(0);
    }

    public List<Doctor> obtenerPorEspecialidadLong(Long especialidad) {
        String query = "select doc " +
                       "from Doctor as doc " +
                       "join doc.especialidades as esp " +
                       "with esp.id =? " +
                       "order by upper(esp.nombre)";
        
        List<Doctor> result = super.getHibernateTemplate()
                    .find(query, especialidad);
        
        return result;
    }
    
    public List<Doctor> obtenerPorEspecialidad(String especialidad) {
        String query = "select doc " +
                       "from Doctor as doc " +
                       "join doc.especialidades as esp " +
                       "with upper(esp.nombre) =? " +
                       "order by upper(esp.nombre)";
        
        List<Doctor> result = super.getHibernateTemplate()
                    .find(query, especialidad.toUpperCase());
        
        return result;
    }
    
    public List<Doctor> getAll(){
        String query = "from Doctor order by upper(lastName)";
        List<Doctor> result = super.getHibernateTemplate().find(query);
        return result;
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

    /** 
     * {@inheritDoc}
     */
    public List<Doctor> obtenerPorDia(DiaDeSemana dia) {
        log.debug("ANTES DE HACER JOIN DEL DOCTOR");
        String query = "from Doctor as doc join doc.horarios as horarios" +
                "with horarios.dia = ?";
        List<Doctor> result = super.getHibernateTemplate().find(query, dia);
        log.debug("DESPUES DE HACER JOIN DEL DOCTOR");
        return result;
    }
}
