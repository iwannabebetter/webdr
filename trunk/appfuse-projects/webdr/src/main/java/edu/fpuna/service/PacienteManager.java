package edu.fpuna.service;

import com.lowagie.text.pdf.codec.postscript.ParseException;
import edu.fpuna.dao.PacienteDao;
import edu.fpuna.dao.RoleDao;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.TipoSangre;

/**
 * @author fmancia
 */
public interface PacienteManager extends GenericManager<Paciente,Long>{

    public void setPacienteDao(PacienteDao dao);
    
    public void setRoleDao(RoleDao roleDao);
    
    public void setTipoSangreManager(GenericManager<TipoSangre, Long> tipoSangreManager);

    public Paciente getPaciente(Long id);

    public Paciente getPaciente(String username);
    
    public Paciente guardarPaciente(Paciente p);
    
    public void borrarPaciente(Paciente p);

    public boolean borrarPaciente(Long id);
    
    public Paciente pacienteAleatorio();
}