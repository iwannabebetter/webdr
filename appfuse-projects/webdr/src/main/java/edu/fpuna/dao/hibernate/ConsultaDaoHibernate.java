/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import edu.fpuna.model.Paciente;
import java.util.List;
/**
 *
 * @author Liz
 */
public class ConsultaDaoHibernate 
        extends GenericDaoHibernate<Consulta, Long> implements ConsultaDao{

    public ConsultaDaoHibernate() {
        super(Consulta.class);
    }
           
    //@Override
    public List<Consulta> obtenerConsultasPaciente(Paciente paciente) {
        super.getHibernateTemplate().find("from Consulta where paciente = ? ", paciente.getUsername());
    }

    //@Override
    public List<Consulta> obtenerConsultasFecha() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //@Override
    public List<Consulta> obtenerConsultasDoctor(String user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
