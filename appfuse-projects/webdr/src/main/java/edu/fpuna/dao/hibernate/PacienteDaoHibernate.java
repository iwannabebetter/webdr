/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Paciente;


/**
 *
 * @author ghuttemann
 */
public class PacienteDaoHibernate 
        extends GenericDaoHibernate<Paciente, Long> implements PacienteDao {

    
    public Paciente getPaciente(Long id) {
        return null;
    }

    
    public Paciente getPaciente(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public boolean guardar(Paciente p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    public boolean borrar(Paciente p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }




    
}
