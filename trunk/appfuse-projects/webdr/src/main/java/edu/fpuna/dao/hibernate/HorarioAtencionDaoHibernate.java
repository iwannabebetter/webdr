/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.HorarioAtencionDao;
import edu.fpuna.model.HorarioAtencion;
import java.util.List;

/**
 *
 * @author Liz
 */
public class HorarioAtencionDaoHibernate 
        extends GenericDaoHibernate<HorarioAtencion, Long> implements HorarioAtencionDao {

    public HorarioAtencionDaoHibernate() {
        super(HorarioAtencion.class);
    }
    
    public HorarioAtencion getHorarioAtencion(Long id) {
        String query = "from HorarioAtencion where id=?";
        List result = getHibernateTemplate().find(query, id);
        
        if (result.isEmpty())
            return null;
        
        return (HorarioAtencion) result.get(0);
    }
    
    public List<HorarioAtencion> getHorarioAtencion(String usernameDoctor) {
        List result = super.getHibernateTemplate().find("from HorarioAtencion where doctor.username = ?", usernameDoctor);
        
        if (result.isEmpty())
            return null;
        
        return result;
    }
    
    public List<HorarioAtencion> getAll() {
        List result = super.getHibernateTemplate().find("from HorarioAtencion ");
        
        if (result.isEmpty())
            return null;
        
        return result;
    }

    public HorarioAtencion guardar(HorarioAtencion p) {
        return super.save(p);
    }

    public void eliminar(HorarioAtencion p) {
        super.remove(p.getId());
    }


}
