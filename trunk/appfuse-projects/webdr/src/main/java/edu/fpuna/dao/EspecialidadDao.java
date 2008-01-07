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
     * Elimina una especialidad dada
     * @param especialidad La especialidad a ser eliminada
     */
    public void eliminar(Especialidad especialidad);
    
    /**
     * Guarda la información de una especialidad
     * @param especialidad
     */
    public void guardar(Especialidad especialidad);
}
