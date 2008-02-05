/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.HorarioAtencion;
import java.util.List;

/**
 *
 * @author Liz
 */
public interface HorarioAtencionDao extends GenericDao<HorarioAtencion, Long> {

    public HorarioAtencion getHorarioAtencion(Long id);
    
    public List<HorarioAtencion> getHorarioAtencion(String usernameDoctor);
    
    public HorarioAtencion guardar(HorarioAtencion p);
    
    public void eliminar(HorarioAtencion p);
    
}

