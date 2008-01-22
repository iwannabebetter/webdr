package edu.fpuna.service.impl;

import edu.fpuna.dao.PacienteDao;
import edu.fpuna.model.Paciente;
import edu.fpuna.service.PacienteManager;
import edu.fpuna.service.*;


/**
 * @author fmancia
 */
public class PacienteManagerImpl extends GenericManagerImpl<Paciente, Long>
        implements PacienteManager {
 
    private PacienteDao dao;

    /**
     * Constructor
     * @param pacienteDao
     */
    public PacienteManagerImpl(PacienteDao pacienteDao) {
        super(pacienteDao);
        this.dao = pacienteDao;
    }

    /**
     * Para que haga el dependency injection
     * @param dao
     */
    public void setPacienteDao(PacienteDao dao) {
        this.dao = dao;
    }



    public Paciente getPaciente(Long id) {
        return dao.getPaciente(id);
    }

    
    public Paciente getPaciente(String username) {
        return dao.getPaciente(username);
    }

    
    public boolean guardarPaciente(Paciente p) {
        return dao.guardar(p);
    }


    public boolean borrarPaciente(Paciente p) {
        return  dao.borrar(p);
    }


    public boolean borrarPaciente(Long id) {
        dao.remove(id);
        Paciente res = dao.get(id);
        if(res != null && res.getFirstName() !=null){
            return false;
        }else{
            return true;
        }
    }
    
}
