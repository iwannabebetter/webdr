/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import javax.persistence.*;

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
    
    public Notas() {
        
    }
    
    public Notas(String sintomas, String diagnostico, String recetario,
            String indicaciones) {
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.recetario = recetario;
        this.indicaciones = indicaciones;
    }
    
    @Column(name="sintomas",nullable=false)
    @Lob
    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Column(name="diagnostico",nullable=false)
    @Lob
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Column(name="recetario")
    @Lob
    public String getRecetario() {
        return recetario;
    }

    public void setRecetario(String recetario) {
        this.recetario = recetario;
    }

    @Column(name="indicaciones")
    @Lob
    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }
}
