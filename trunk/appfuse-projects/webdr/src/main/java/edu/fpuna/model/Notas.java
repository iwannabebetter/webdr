/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clase utilizada para representar datos de una consulta:
 * diagnóstico, síntomas, indicaciones, recetario.
 * @author ghuttemann
 */
@Embeddable
public class Notas {
    
    private String sintomas;
    private String diagnostico;
    private String recetario;
    private String indicaciones;
    
    @Column(name="sintomas",nullable=false)
    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Column(name="diagnostico",nullable=false)
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Column(name="recetario")
    public String getRecetario() {
        return recetario;
    }

    public void setRecetario(String recetario) {
        this.recetario = recetario;
    }

    @Column(name="indicaciones")
    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
