package edu.fpuna.service;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import java.util.Date;
import java.util.List;

/**
 * Implementación de la Logica de Negocios para la Consulta
 * @author Administrador
 */
public interface ConsultaManager extends GenericManager<Consulta,Long>{

    public void setConsultaDao(ConsultaDao dao);

    public Consulta getConsulta(long id);

    public List getConsultasPaciente(String username);
    
    public List getConsultasDoctor(String username);

    public List getConsultasFecha(Date fecha);

    public void saveConsulta(Consulta consulta);

    public void removeConsulta(long id);
    
    public List<Consulta> getAll();
 
}
