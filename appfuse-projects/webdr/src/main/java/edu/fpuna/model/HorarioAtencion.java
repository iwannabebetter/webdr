/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author ghuttemann
 */
@Entity
@Table(name="horario")
public class HorarioAtencion extends BaseObject {
    
    private Long id;
    private DiaDeSemana dia;
    private Date horaInicio;
    private Date horaFin;
    private Doctor doctor;
    private Set<Turno> turnos = new HashSet<Turno>();

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="dia",nullable=false)
    @Enumerated(EnumType.ORDINAL)
    public DiaDeSemana getDia() {
        return dia;
    }

    public void setDia(DiaDeSemana dia) {
        this.dia = dia;
    }

    @Column(name="hora_inicio",nullable=false)
    @Temporal(TemporalType.TIME)
    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Column(name="hora_fin",nullable=false)
    @Temporal(TemporalType.TIME)
    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
    
    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn(name="doctor_id",nullable=false)
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="horario_id",nullable=false)
    @OrderBy(value="hora")
    public Set<Turno> getTurnos() {
        return turnos;
    }
    
    public void setTurnos(Set<Turno> turnos) {
        this.turnos = turnos;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + this.id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HorarioAtencion))
            return false;
        
        final HorarioAtencion horario = (HorarioAtencion) o;
        
        if (this.id.equals(horario.getId()))
            return true;
        
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
