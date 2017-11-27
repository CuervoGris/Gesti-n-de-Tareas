/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias;

import org.Librerias.Grafos.AristaDirigida;
import org.Librerias.Grafos.DigrafoAristaPonderada;
import org.Librerias.EstructasBase.Pila;

/**
 *
 * @author assi
 */
public class LPAciclico {
    private double[] distanciaHacia;          // distanciaHacia[v] = distance  of longest s->v path
    private AristaDirigida[] aristaHacia;    // edgeTo[v] = last edge on longest s->v path

    /**
     * Computes a longest paths tree desde <tt>s</tt> hacia every other vertex in
 the directed acyclic graph <tt>G</tt>.
     * @param G the acyclic digraph
     * @param s the source vertex
     * @throws IllegalArgumentException if the digraph is not acyclic
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public LPAciclico(DigrafoAristaPonderada G, int s) {
        distanciaHacia = new double[G.V()];
        aristaHacia = new AristaDirigida[G.V()];
        for (int v = 0; v < G.V(); v++)
            distanciaHacia[v] = Double.NEGATIVE_INFINITY;
        distanciaHacia[s] = 0.0;

        // relax vertices in toplogical orden
        Topologico topological = new Topologico(G);
        if (!topological.tieneOrden())
            throw new IllegalArgumentException("El dígrafo no es acíclico.");
        for (int v : topological.orden()) {
            for (AristaDirigida a : G.ady(v))
                relajar(a);
        }
    }

    // relax edge e, but update if you find a *longer* path
    private void relajar(AristaDirigida e) {
        int v = e.desde(), w = e.hacia();
        if (distanciaHacia[w] < distanciaHacia[v] + e.peso()) {
            distanciaHacia[w] = distanciaHacia[v] + e.peso();
            aristaHacia[w] = e;
        }       
    }

    /**
     * Returns the length of a longest path desde the source vertex <tt>s</tt> hacia vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return the length of a longest path desde the source vertex <tt>s</tt> hacia vertex <tt>v</tt>;
     *    <tt>Double.NEGATIVE_INFINITY</tt> if no such path
     */
    public double distanciaHacia(int v) {
        return distanciaHacia[v];
    }

    /**
     * Is there a path desde the source vertex <tt>s</tt> hacia vertex <tt>v</tt>?
     * @param v the destination vertex
     * @return <tt>true</tt> if there is a path desde the source vertex
    <tt>s</tt> hacia vertex <tt>v</tt>, and <tt>false</tt> otherwise
     */
    public boolean tieneCaminoHacia(int v) {
        return distanciaHacia[v] > Double.NEGATIVE_INFINITY;
    }

    /**
     * Returns a longest path desde the source vertex <tt>s</tt> hacia vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return a longest path desde the source vertex <tt>s</tt> hacia vertex <tt>v</tt>
    as an iterable of aristas, and <tt>null</tt> if no such path
     */
    public Iterable<AristaDirigida> caminoHacia(int v) {
        if (!tieneCaminoHacia(v)) return null;
        Pila<AristaDirigida> camino = new Pila<AristaDirigida>();
        for (AristaDirigida a = aristaHacia[v]; a != null; a = aristaHacia[a.desde()]) {
            camino.push(a);
        }
        return camino;
    }
}
