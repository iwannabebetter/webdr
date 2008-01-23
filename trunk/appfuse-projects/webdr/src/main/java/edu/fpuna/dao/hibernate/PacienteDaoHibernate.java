/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Address;
import edu.fpuna.model.Paciente;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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

    public Paciente guardar(Paciente p) {
        return super.save(p);
    }

    public void borrar(Paciente p) {
        super.remove(p.getId());
    }    
    

    
}
