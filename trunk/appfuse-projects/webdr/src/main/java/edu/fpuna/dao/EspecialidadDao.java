/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Especialidad;

/**
 * Interfaz DAO para Especialidad.
 * @author ghuttemann
 */
public interface EspecialidadDao extends GenericDao<Especialidad, Long> {
    /**
     * Obtiene una especialidad por su nombre
     * @param nombre El nombre de la especialidad
     * @return El objeto especialidad recuperado
     */
    public Especialidad obtenerPorNombre(String nombre);

    /**
     * Obtiene una especialidad por su nombre
     * @param id El id de la especialidad
     * @return El objeto especialidad recuperado
     */    
    public Especialidad obtenerPorId(Long id);
    
    /**
     * Elimina una especialidad dada
     * @param especialidad La especialidad a ser eliminada
     */
    public void eliminar(Especialidad especialidad);
    
    /**
     * Guarda la información de una especialidad
     * @param especialidad
     */
    public Especialidad guardar(Especialidad especialidad);
}
