/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias;

import org.Librerias.Grafos.CicloDirigido;
import org.Librerias.Grafos.CicloDirigidoDeAristaPonderada;
import org.Librerias.Grafos.DigrafoAristaPonderada;
import org.Librerias.Grafos.Digrafo;

/**
 *
 * @author assi
 */
public class Topologico {
    private Iterable<Integer> orden;  // topological orden
    private int[] rango;      // rango[v] = position of vertex v in topological orden

    /**
     * Determines whether the digraph <tt>G</tt> has a topological orden and, if so,
 finds such a topological orden.
     * @param G the digraph
     */
    public Topologico(Digrafo G) {
        CicloDirigido descubridor = new CicloDirigido(G);
        if (!descubridor.tieneCiclo()) {
            OrdenPrimeraProfundidad dfs = new OrdenPrimeraProfundidad(G);
            orden = dfs.postReverso();
            rango = new int[G.V()];
            int i = 0;
            for (int v : orden)
                rango[v] = i++;
        }
    }

    /**
     * Determines whether the edge-weighted digraph <tt>G</tt> has a topological
 orden and, if so, finds such an orden.
     * @param G the edge-weighted digraph
     */
    public Topologico(DigrafoAristaPonderada G) {
        CicloDirigidoDeAristaPonderada descubridor = 
                new CicloDirigidoDeAristaPonderada(G);
        if (!descubridor.tieneCiclo()) {
            OrdenPrimeraProfundidad dfs = new OrdenPrimeraProfundidad(G);
            orden = dfs.postReverso();
        }
    }

    /**
     * Returns a topological orden if the digraph has a topologial orden,
 and <tt>null</tt> otherwise.
     * @return a topological orden of the vertices (as an interable) if the
    digraph has a topological orden (or equivalently, if the digraph is a DAG),
    and <tt>null</tt> otherwise
     */
    public Iterable<Integer> orden() {
        return orden;
    }

    /**
     * Does the digraph have a topological orden?
     * @return <tt>true</tt> if the digraph has a topological orden (or equivalently,
    if the digraph is a DAG), and <tt>false</tt> otherwise
     */
    public boolean tieneOrden() {
        return orden != null;
    }

    /**
     * The the rango of vertex <tt>v</tt> in the topological orden;
 -1 if the digraph is not a DAG
     * @return the position of vertex <tt>v</tt> in a topological orden
    of the digraph; -1 if the digraph is not a DAG
     * @throws IndexOutOfBoundsException unless <tt>v</tt> is between 0 and
     *    <em>V</em> &minus; 1
     */
    public int rango(int v) {
        validarVertice(v);
        if (tieneOrden()) return rango[v];
        else              return -1;
    }

    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validarVertice(int v) {
        int V = rango.length;
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException(
                    "vértice " + v + " no está entre 0 y " + (V-1));
    }
}
