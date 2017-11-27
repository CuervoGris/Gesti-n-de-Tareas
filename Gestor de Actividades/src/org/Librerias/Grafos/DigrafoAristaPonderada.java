/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias.Grafos;

import org.Librerias.EstructasBase.Bolsa;
import org.Librerias.EstructasBase.Pila;
import java.util.Iterator;

/**
 *
 * @author assi
 */
public class DigrafoAristaPonderada {
    private static final String NUEVALINEA = System.getProperty(
            "line.separator");

    private final int V;
    private int E;
    private Bolsa<AristaDirigida>[] ady;
    
    /**
     * Initializes an empty edge-weighted digraph with <tt>V</tt> vertices and 0 aristas.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    public DigrafoAristaPonderada(int V) {
        if (V < 0) throw new IllegalArgumentException(
                "Los números de los vértices en el Digrafo no deben"
                        + "ser negativos");
        this.V = V;
        this.E = 0;
        ady = (Bolsa<AristaDirigida>[]) new Bolsa[V];
        for (int v = 0; v < V; v++)
            ady[v] = new Bolsa<AristaDirigida>();
    }

    /**
     * Initializes a new edge-weighted digraph that is a deep copy of <tt>G</tt>.
     *
     * @param  G the edge-weighted digraph hacia copy
     */
    public DigrafoAristaPonderada(DigrafoAristaPonderada G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Pila<AristaDirigida> reverso = new Pila<AristaDirigida>();
            for (Iterator it = G.ady[v].iterator(); it.hasNext();) {
                AristaDirigida e = (AristaDirigida) it.next();
                reverso.push(e);
            }
            for (AristaDirigida e : reverso) {
                ady[v].agregar(e);
            }
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted digraph.
     *
     * @return the number of vertices in this edge-weighted digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of aristas in this edge-weighted digraph.
     *
     * @return the number of aristas in this edge-weighted digraph
     */
    public int E() {
        return E;
    }

    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validarVertice(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException(
                    "vértice" + v + " no está entre 0 y " + (V-1));
    }

    /**
     * Adds the directed edge <tt>e</tt> hacia this edge-weighted digraph.
     *
     * @param  a the edge
     * @throws IndexOutOfBoundsException unless endpoints of edge are between 0 and V-1
     */
    public void agregarArista(AristaDirigida a) {
        int v = a.desde();
        int w = a.hacia();
        validarVertice(v);
        validarVertice(w);
        ady[v].agregar(a);
        E++;
    }


    /**
     * Returns the directed aristas incident desde vertex <tt>v</tt>.
     *
     * @param  v the vertex
     * @return the directed aristas incident desde vertex <tt>v</tt> as an Iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<AristaDirigida> ady(int v) {
        validarVertice(v);
        return ady[v];
    }

    /**
     * Returns the number of directed aristas incident desde vertex <tt>v</tt>.
     * This is known as the <em>gradoSalida</em> of vertex <tt>v</tt>.
     *
     * @param  v the vertex
     * @return the gradoSalida of vertex <tt>v</tt>
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public int gradoSalida(int v) {
        validarVertice(v);
        return ady[v].tamanno();
    }

    /**
     * Returns all directed aristas in this edge-weighted digraph.
     * To iterate over the aristas in this edge-weighted digraph, use foreach notation:
 <tt>for (AristaDirigida e : G.aristas())</tt>.
     *
     * @return all aristas in this edge-weighted digraph, as an iterable
     */
    public Iterable<AristaDirigida> aristas() {
        Bolsa<AristaDirigida> lista = new Bolsa<AristaDirigida>();
        for (int v = 0; v < V; v++) {
            for (AristaDirigida e : ady(v)) {
                lista.agregar(e);
            }
        }
        return lista;
    } 

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of aristas <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of aristas
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NUEVALINEA);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Iterator it = ady[v].iterator(); it.hasNext();) {
                AristaDirigida e = (AristaDirigida) it.next();
                s.append(e + "  ");
            }
            s.append(NUEVALINEA);
        }
        return s.toString();
    }
}
