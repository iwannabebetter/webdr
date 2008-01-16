package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import edu.fpuna.model.User;
import java.util.Date;
import java.util.List;
/**
 * Implementación del DAO correspondiente al manejo de Consultas
 * @author mrodas
 */
public class ConsultaDaoHibernate 
        extends GenericDaoHibernate<Consulta, Long> implements ConsultaDao {

    public ConsultaDaoHibernate() {
        super(Consulta.class);
    }

    /**
     * Metodo para obtener las Consultas de un Paciente.
     * @params username Nombre del Paciente.
     * @return List<Consultas> Lista de Consultas del Paciente.
     */
    public List<Consulta> obtenerConsultasPaciente(String username) {
        long paciente_id = this.obtenerIdUsuario(username);
        String query = "from Consulta where id=?";
        
        log.debug("##Realizando consulta de paciente: " + paciente_id);
        List result = super.getHibernateTemplate().find(query, paciente_id);
        log.debug("##Consulta encontrada.");
        
        return result;
    }
    
    
    /**
     * Metodo para obtener las Consultas de un Doctor.
     * @param username Nombre del Doctor.
     * @return List<Consultas> Lista de Consultas del Doctor.
     */
    public List<Consulta> obtenerConsultasDoctor(String username) {
        long doctor_id = this.obtenerIdUsuario(username);
        String query = "from Consulta where doctor_id=?";
        
        return getHibernateTemplate().find(query, doctor_id);
    }
    
    private long obtenerIdUsuario(String username) {
        String query = "from User where username=?";
        
        log.debug("EMPEZAMOS LA CONSULTA SOBRE APPUSER.....");
        List<User> list = getHibernateTemplate().find(query, username);
        log.debug("PASAMOS LA CONSULTA SOBRE APPUSER.....");
        
        long retorno = 0;
        if (list.isEmpty()) {
            log.debug("# "+username+" NO EXISTE EN APPUSER.");
        } else {
            retorno = list.get(0).getId();
        }
        
        log.debug("EL VALOR OBTENIDO DE APPUSER: "+retorno);
        return retorno;
    }

    //@Override
    public List<Consulta> obtenerConsultasFecha(Date fecha) {
        String query = "from consulta where fecha=?";
        return getHibernateTemplate().find(query, fecha);
    }
    
    //@Override
    public void eliminar(Consulta consulta) {
        super.remove(consulta.getId());
    }

    //@Override
    public void guardar(Consulta consulta) {
        super.save(consulta);
    }
}
