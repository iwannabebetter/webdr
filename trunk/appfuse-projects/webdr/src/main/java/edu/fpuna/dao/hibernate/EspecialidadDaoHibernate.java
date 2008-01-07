/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.EspecialidadDao;
import edu.fpuna.model.Especialidad;
import java.util.List;

/**
 *
 * @author ghuttemann
 */
public class EspecialidadDaoHibernate 
    extends GenericDaoHibernate<Especialidad, Long> implements EspecialidadDao {
    
    public EspecialidadDaoHibernate() {
        super(Especialidad.class);
    }

    //@Override
    public Especialidad obtenerPorNombre(String nombre) {
        String query = "from Especialidad where nombre=?";
        List result = getHibernateTemplate().find(query, nombre);
        
        if (result.isEmpty())
            return null;
        
        return (Especialidad) result.get(0);
    }

    //@Override
    public void eliminar(Especialidad especialidad) {
        super.remove(especialidad.getId());
    }

    //@Override
    public void guardar(Especialidad especialidad) {
        super.save(especialidad);
    }
}
