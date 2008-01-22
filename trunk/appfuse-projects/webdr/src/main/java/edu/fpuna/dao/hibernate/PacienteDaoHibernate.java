/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Paciente;
import java.util.List;


/**
 *
 * @author ghuttemann
 */
public class PacienteDaoHibernate 
        extends GenericDaoHibernate<Paciente, Long> implements PacienteDao {

    
    public Paciente getPaciente(Long id) {
        String query = "from Paciente where id=?";
        List result = getHibernateTemplate().find(query, id);
        
        if (result.isEmpty())
            return null;
        
        return (Paciente) result.get(0);
    }

    
    public Paciente getPaciente(String username) {
        throw new UnsupportedOperationException("No implementamos todavia");
    }

    
    public boolean guardar(Paciente p) {
        super.save(p);
    }

    
    public boolean borrar(Paciente p) {
        super.remove(p.getId());
    }




    
}
