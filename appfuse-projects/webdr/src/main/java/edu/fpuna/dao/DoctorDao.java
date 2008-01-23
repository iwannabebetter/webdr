/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import java.util.List;

/**
 * Interfaz DAO para Doctor.
 * @author ghuttemann
 */
public interface DoctorDao extends GenericDao<Doctor, Long> {
    
    /**
     * Recupera los doctores con una determinada especialidad
     * @param especialidad La especialidad buscada en los doctores
     * @return Una lista de los doctores que cumplen el criterio
     */
    public List<Doctor> obtenerPorEspecialidad(Especialidad especialidad);
}
