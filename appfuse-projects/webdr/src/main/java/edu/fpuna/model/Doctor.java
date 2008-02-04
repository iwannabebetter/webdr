/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import edu.fpuna.Constants.FormatoFecha;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import javax.persistence.*;

/**
 * Clase que representa a un doctor
 * @author ghuttemann
 */
@Entity
@Table(name="doctor")
public class Doctor extends User {
    
    private Date fechaNacimiento;
    private Integer registro;
    private Set<Especialidad> especialidades = new HashSet<Especialidad>();
    private Set<HorarioAtencion> horarios = new HashSet<HorarioAtencion>();
    
    @Column(name="fechanac",nullable=false)
    @Temporal(TemporalType.DATE)
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /*
     * Retorna la fechaNacimiento en formato texto
     */
    @Transient
    public String getFechaNacimientoString() {
        return super.formatearFecha(fechaNacimiento, FormatoFecha.FECHA);
    }
    
    @Column(name="registro",unique=true,nullable=false)
    public Integer getRegistro() {
        return registro;
    }
    
    public void setRegistro(Integer registro) {
        this.registro = registro;
    }
    
    public void agregarEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
    }
    
    public void eliminarEspecialidad(Especialidad especialidad) {
        this.especialidades.remove(especialidad);
    }
    
    public void agregarHorario(HorarioAtencion horario) {
        this.horarios.add(horario);
    }
    
    public void eliminarHorario(HorarioAtencion horario) {
        this.horarios.remove(horario);
    }

    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
    @JoinTable(
        name = "doctor_especialidad",
        joinColumns = { @JoinColumn(name="doctor_id") },
        inverseJoinColumns = @JoinColumn(name="especialidad_id")
    )
    @OrderBy(value="nombre")
    public Set<Especialidad> getEspecialidades() {
        return especialidades;
    }

    
    /**
     * Convert user roles to LabelValue objects for convenience.
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getEspecialidadList() {
        List<LabelValue> doctorEspecialidades = new ArrayList<LabelValue>();

        if (this.especialidades != null) {
            for (Especialidad especialidad : especialidades) {
                // convert the user's roles to LabelValue Objects
                doctorEspecialidades.add(new LabelValue( especialidad.getNombre(), especialidad.getNombre()));
            }
        }

        return doctorEspecialidades;
    }
    
    
    public void setEspecialidades(Set<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }
    
    @OneToMany(fetch=FetchType.EAGER)
    @OrderBy(value="dia")
    public Set<HorarioAtencion> getHorarios() {
        return horarios;
    }
    
    public void setHorarios(Set<HorarioAtencion> horarios) {
        this.horarios = horarios;
    }
}
