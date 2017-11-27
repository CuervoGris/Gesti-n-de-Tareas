/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias.Grafos;

import org.Librerias.EstructasBase.Pila;

/**
 *
 * @author assi
 */
public class CicloDirigido {
    private boolean[] marcado;        // marked[v] = has vertex v been marked?
    private int[] aristaHacia;            // edgeTo[v] = previous vertex on path to v
    private boolean[] enPila;       // onStack[v] = is vertex on the stack?
    private Pila<Integer> ciclo;    // directed ciclo (or null if no such ciclo)

    /**
     * Determines whether the digraph <tt>G</tt> has a directed ciclo and, if so,
 finds such a ciclo.
     * @param G the digraph
     */
    public CicloDirigido(Digrafo G) {
        marcado  = new boolean[G.V()];
        enPila = new boolean[G.V()];
        aristaHacia  = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marcado[v] && ciclo == null) dfs(G, v);
    }

    // check that algorithm computes either the topological order or finds a directed ciclo
    private void dfs(Digrafo G, int v) {
        enPila[v] = true;
        marcado[v] = true;
        for (int w : G.ady(v)) {

            // short circuit if directed ciclo found
            if (ciclo != null) return;

            //found new vertex, so recur
            else if (!marcado[w]) {
                aristaHacia[w] = v;
                dfs(G, w);
            }

            // trace back directed ciclo
            else if (enPila[w]) {
                ciclo = new Pila<Integer>();
                for (int x = v; x != w; x = aristaHacia[x]) {
                    ciclo.push(x);
                }
                ciclo.push(w);
                ciclo.push(v);
                assert revisar();
            }
        }
        enPila[v] = false;
    }

    /**
     * Does the digraph have a directed ciclo?
     * @return <tt>true</tt> if the digraph has a directed ciclo, <tt>false</tt> otherwise
     */
    public boolean tieneCiclo() {
        return ciclo != null;
    }

    /**
     * Returns a directed ciclo if the digraph has a directed ciclo, and <tt>null</tt> otherwise.
     * @return a directed ciclo (as an iterable) if the digraph has a directed ciclo,
    and <tt>null</tt> otherwise
     */
    public Iterable<Integer> ciclo() {
        return ciclo;
    }


    // certify that digraph has a directed ciclo if it reports one
    private boolean revisar() {

        if (tieneCiclo()) {
            // verify ciclo
            int primero = -1, ultimo = -1;
            for (int v : ciclo()) {
                if (primero == -1) primero = v;
                ultimo = v;
            }
            if (primero != ultimo) {
                System.err.printf("el ciclo comienza con %d y termina con %d\n",
                        primero, ultimo);
                return false;
            }
        }


        return true;
    }
}
