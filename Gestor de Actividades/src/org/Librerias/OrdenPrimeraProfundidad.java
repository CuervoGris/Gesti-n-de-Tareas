/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias;

import org.Librerias.Grafos.AristaDirigida;
import org.Librerias.Grafos.DigrafoAristaPonderada;
import org.Librerias.Grafos.Digrafo;
import org.Librerias.EstructasBase.Pila;
import org.Librerias.EstructasBase.Cola;

/**
 *
 * @author assi
 */
public class OrdenPrimeraProfundidad {
    private boolean[] marcado;          // marked[v] = has v been marked in dfs?
    private int[] pre;                 // pre[v]    = preorder  number of v
    private int[] pos;                // pos[v]   = postorder number of v
    private Cola<Integer> preorden;   // vertices in preorder
    private Cola<Integer> posorden;  // vertices in postorder
    private int contadorPre;            // counter or preorder numbering
    private int contadorPos;           // counter for postorder numbering

    /**
     * Determines a depth-first order for the digraph <tt>G</tt>.
     * @param G the digraph
     */
    public OrdenPrimeraProfundidad(Digrafo G) {
        pre    = new int[G.V()];
        pos   = new int[G.V()];
        posorden = new Cola<Integer>();
        preorden  = new Cola<Integer>();
        marcado    = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marcado[v]) dfs(G, v);
    }

    /**
     * Determines a depth-first order for the edge-weighted digraph <tt>G</tt>.
     * @param G the edge-weighted digraph
     */
    public OrdenPrimeraProfundidad(DigrafoAristaPonderada G) {
        pre    = new int[G.V()];
        pos   = new int[G.V()];
        posorden = new Cola<Integer>();
        preorden  = new Cola<Integer>();
        marcado    = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marcado[v]) dfs(G, v);
    }

    // run DFS in digraph G desde vertex v and compute preorder/postorder
    private void dfs(Digrafo G, int v) {
        marcado[v] = true;
        pre[v] = contadorPre++;
        preorden.entrarACola(v);
        for (int w : G.ady(v)) {
            if (!marcado[w]) {
                dfs(G, w);
            }
        }
        posorden.entrarACola(v);
        pos[v] = contadorPos++;
    }

    // run DFS in edge-weighted digraph G desde vertex v and compute preorder/postorder
    private void dfs(DigrafoAristaPonderada G, int v) {
        marcado[v] = true;
        pre[v] = contadorPre++;
        preorden.entrarACola(v);
        for (AristaDirigida e : G.ady(v)) {
            int w = e.hacia();
            if (!marcado[w]) {
                dfs(G, w);
            }
        }
        posorden.entrarACola(v);
        pos[v] = contadorPos++;
    }

    /**
     * Returns the preorder number of vertex <tt>v</tt>.
     * @param v the vertex
     * @return the preorder number of vertex <tt>v</tt>
     */
    public int pre(int v) {
        return pre[v];
    }

    /**
     * Returns the postorder number of vertex <tt>v</tt>.
     * @param v the vertex
     * @return the postorder number of vertex <tt>v</tt>
     */
    public int pos(int v) {
        return pos[v];
    }

    /**
     * Returns the vertices in postorder.
     * @return the vertices in postorder, as an iterable of vertices
     */
    public Iterable<Integer> pos() {
        return posorden;
    }

    /**
     * Returns the vertices in preorder.
     * @return the vertices in preorder, as an iterable of vertices
     */
    public Iterable<Integer> pre() {
        return preorden;
    }

    /**
     * Returns the vertices in reverso postorder.
     * @return the vertices in reverso postorder, as an iterable of vertices
     */
    public Iterable<Integer> postReverso() {
        Pila<Integer> reverso = new Pila<Integer>();
        for (int v : posorden)
            reverso.push(v);
        return reverso;
    }
}
