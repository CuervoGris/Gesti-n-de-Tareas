/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias.Grafos;

import org.Librerias.EstructasBase.Bolsa;
import org.Librerias.EstructasBase.Pila;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author assi
 */
public class Digrafo {
    private static final String NUEVALINEA = 
            System.getProperty("line.separator");

    private final int V;
    private int A;
    private Bolsa<Integer>[] ady;
    
    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if V < 0
     */
    public Digrafo(int V) {
        if (V < 0) throw new IllegalArgumentException(
                "El número de vértices en un Dígrafo no debe ser negativo");
        this.V = V;
        this.A = 0;
        ady = (Bolsa<Integer>[]) new Bolsa[V];
        for (int v = 0; v < V; v++) {
            ady[v] = new Bolsa<Integer>();
        }
    }

    
    /**
     * Initializes a new digraph that is a deep copy of <tt>G</tt>.
     *
     * @param  G the digraph to copy
     */
    public Digrafo(Digrafo G) {
        this(G.V());
        this.A = G.A();
        for (int v = 0; v < G.V(); v++) {
            // reverso so that adjacency list is in same order as original
            Pila<Integer> reverso = new Pila<Integer>();
            for (Iterator it = G.ady[v].iterator(); it.hasNext();) {
                int w = (int) it.next();
                reverso.push(w);
            }
            for (int w : reverso) {
                ady[v].agregar(w);
            }
        }
    }
        
    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    public int A() {
        return A;
    }


    // throw an IndexOutOfBoundsException unless 0 <= v < V
    private void validarVertice(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException(
                    "vértice " + v + " no está entre 0 y " + (V-1));
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param  v the tail vertex
     * @param  w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public void agregarArista(int v, int w) {
        validarVertice(v);
        validarVertice(w);
        ady[v].agregar(w);
        A++;
    }
    


    /**
     * Returns the vertices adjacent from vertex <tt>v</tt> in this digraph.
     *
     * @param  v the vertex
     * @return the vertices adjacent from vertex <tt>v</tt> in this digraph, as an iterable
     * @throws IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Integer> ady(int v) {
        validarVertice(v);
        return ady[v];
    }

    /**
     * Returns the number of directed edges incident from vertex <tt>v</tt>.
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
     * Returns the reverso of the digraph.
     *
     * @return the reverso of the digraph
     */
    public Digrafo reverso() {
        Digrafo R = new Digrafo(V);
        for (int v = 0; v < V; v++) {
            for (int w : ady(v)) {
                R.agregarArista(w, v);
            }
        }
        return R;
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>A</em>,  
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vértices, " + A + " aristas " + NUEVALINEA);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (Iterator it = ady[v].iterator(); it.hasNext();) {
                int w = (int) it.next();
                s.append(String.format("%d ", w));
            }
            s.append(NUEVALINEA);
        }
        return s.toString();
    }
}
