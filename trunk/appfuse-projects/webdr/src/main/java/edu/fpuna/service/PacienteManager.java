package edu.fpuna.service;

import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Paciente;

/**
 * @author fmancia
 */
public interface PacienteManager extends GenericManager<Paciente,Long>{

    public void setPacienteDao(PacienteDao dao);

    public Paciente getPaciente(Long id);

    public Paciente getPaciente(String username);
    
    public boolean guardarPaciente(Paciente p);
    
    public boolean borrarPaciente(Paciente p);

    public boolean borrarPaciente(Long id);
}