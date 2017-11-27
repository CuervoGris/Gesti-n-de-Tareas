/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias.Grafos;

/**
 *
 * @author assi
 */
public class AristaDirigida {
    private final int v;
    private final int w;
    private final double peso;
    
    public AristaDirigida(int v, int w, double peso) {
        if (v < 0) throw new IndexOutOfBoundsException(
                "Los nombres de los vértices deben ser enteros no negativos");
        if (w < 0) throw new IndexOutOfBoundsException(
                "Los nombres de los vértices deben ser enteros no negativos");
        if (Double.isNaN(peso)) throw new IllegalArgumentException(
                "Peso es NaN");
        this.v = v;
        this.w = w;
        this.peso = peso;
    }

    public int desde() {
        return v;
    }

    public int hacia() {
        return w;
    }

    public double peso() {
        return peso;
    }

    @Override
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", peso);
    }
}
