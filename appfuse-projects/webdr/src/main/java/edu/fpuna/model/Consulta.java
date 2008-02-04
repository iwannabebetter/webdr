/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fpuna.model;

import edu.fpuna.Constants.FormatoFecha;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 *
 * @author ghuttemann
 */
@Entity
@Table(name="consulta")
public class Consulta extends BaseObject {

    private Long id;
    private Timestamp fecha;
    private MedidasPaciente medidasPaciente;
    private Notas notas;
    private Doctor doctor;
    private Paciente paciente;

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="fecha",nullable=false)
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    /*
     * Retorna la fecha en formato texto
     */
    @Transient
    public String getFechaString() {
        return super.formatearFecha(fecha, FormatoFecha.FECHAHORA);
    }

    @Embedded
    public MedidasPaciente getMedidasPaciente() {
        return medidasPaciente;
    }

    public void setMedidasPaciente(MedidasPaciente medidasPaciente) {
        this.medidasPaciente = medidasPaciente;
    }
    
    @Embedded
    public Notas getNotas() {
        return notas;
    }

    public void setNotas(Notas notas) {
        this.notas = notas;
    }
    
    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn(name="doctor_id",nullable=false)
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn(name="paciente_id",nullable=false)
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + this.id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Consulta)) {
            return false;
        }

        final Consulta consulta = (Consulta) o;

        if (this.getId().equals(consulta.getId())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        if (this.getId() == null)
            return 0;
        
        return this.getId().hashCode();
    }
}
