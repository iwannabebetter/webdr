/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;

/**
 * Clase que representa a un doctor
 * @author ghuttemann
 */
@Entity
@Table(name="doctor")
public class Doctor extends User {
    
    private Set<Especialidad> especialidades = new HashSet<Especialidad>();
    
    public void agregarEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
    }
    
    public void eliminarEspecialidad(Especialidad especialidad) {
        this.especialidades.remove(especialidad);
    }

    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
    @JoinTable(
        name = "doctor_especialidad",
        joinColumns = { @JoinColumn( name="doctor_id") },
        inverseJoinColumns = @JoinColumn( name="especialidad_id")
    )
    public Set<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Set<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }
}
