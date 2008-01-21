/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Paciente;

/**
 *
 * @author ghuttemann
 */
public interface PacienteDao extends GenericDao<Paciente, Long>{

    
    public Paciente getPaciente(Long id);
    
    public Paciente getPaciente(String username);
    
    public boolean guardar(Paciente p);
    
    public boolean borrar(Paciente p);

}
