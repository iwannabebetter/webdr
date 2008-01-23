/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.DoctorDao;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import java.util.List;

/**
 * Implementación Hibernate del DAO correspondiente al manejo de Doctor
 * @author ghuttemann
 */
public class DoctorDaoHibernate 
    extends GenericDaoHibernate<Doctor, Long> implements DoctorDao {
    
    public DoctorDaoHibernate() {
        super(Doctor.class);
    }

    public List<Doctor> obtenerPorEspecialidad(Especialidad especialidad) {
        String query = "from Doctor as doc " +
                       "join doc.especialidades as esp " +
                       "with esp.nombre=?";
        
        List<Doctor> result = super.getHibernateTemplate()
                                   .find(query, especialidad.getNombre());
        
        return result;
    }
}
