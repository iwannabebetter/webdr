/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.HorarioAtencionDao;
import edu.fpuna.dao.TurnoDao;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import java.util.List;

/**
 *
 * @author Liz
 */
public class TurnoDaoHibernate 
        extends GenericDaoHibernate<Turno, Long> implements TurnoDao {

    public TurnoDaoHibernate() {
        super(Turno.class);
    }
    
    public Turno getTurno(Long id) {
        String query = "from Turno where id=?";
        List result = getHibernateTemplate().find(query, id);
        
        if (result.isEmpty())
            return null;
        
        return (Turno) result.get(0);
    }
    
    public List<Turno> getTurnos(HorarioAtencion horario) {
        List result = super.getHibernateTemplate().find(
                " from Turno where horario_id = ? ", horario.getId());
        
        return result;
    }
    
    public List<Turno> getAll() {
        List result = super.getHibernateTemplate().find("from Turno");
        
        return result;
    }

    public Turno guardar(Turno t) {
        return super.save(t);
    }

    public void eliminar(Turno t) {
        super.remove(t.getId());
    }


}
