/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import edu.fpuna.Constants.FormatoFecha;
import java.sql.Time;
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
    private Time horaInicio;
    private Time horaFin;
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

    @Transient
    public String getDiaString() {
        return this.dia.toString();
    }
    
    @Column(name="hora_inicio",nullable=false)
    public Time getHoraInicio() {
        return horaInicio;
    }
    
    @Transient
    public String getHoraInicioString() {
        return super.formatearFecha(horaInicio, FormatoFecha.HORA);
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Column(name="hora_fin",nullable=false)
    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
    
    @Transient
    public String getHoraFinString() {
        return super.formatearFecha(horaFin, FormatoFecha.HORA);
    }
    
    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn(name="doctor_id",nullable=false)
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    @OneToMany(fetch=FetchType.EAGER,mappedBy="horario")
    @OrderBy(value="hora")
    public Set<Turno> getTurnos() {
        return turnos;
    }
    
    public void addTurno(Turno turno) {
        turnos.add(turno);
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