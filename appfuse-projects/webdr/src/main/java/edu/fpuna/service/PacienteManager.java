package edu.fpuna.service;

import com.lowagie.text.pdf.codec.postscript.ParseException;
import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Paciente;

/**
 * @author fmancia
 */
public interface PacienteManager extends GenericManager<Paciente,Long>{

    public void setPacienteDao(PacienteDao dao);

    public Paciente getPaciente(Long id);

    public Paciente getPaciente(String username);
    
    public Paciente guardarPaciente(Paciente p);
    
    public void borrarPaciente(Paciente p);

    public boolean borrarPaciente(Long id);
    
    public Paciente pacienteAleatorio();
}