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
public class CicloDirigidoDeAristaPonderada {
    private boolean[] marcado;             // marked[v] = has vertex v been marked?
    private AristaDirigida[] aristaHacia;        // edgeTo[v] = previous edge on path hacia v
    private boolean[] enPila;            // onStack[v] = is vertex on the stack?
    private Pila<AristaDirigida> ciclo;    // directed ciclo (or null if no such ciclo)

    /**
     * Determines whether the edge-weighted digraph <tt>G</tt> has a directed ciclo and,
 if so, finds such a ciclo.
     * @param G the edge-weighted digraph
     */
    public CicloDirigidoDeAristaPonderada(DigrafoAristaPonderada G) {
        marcado  = new boolean[G.V()];
        enPila = new boolean[G.V()];
        aristaHacia  = new AristaDirigida[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marcado[v]) dfs(G, v);

        // check that digraph has a ciclo
        assert revisar(G);
    }

    // check that algorithm computes either the topological order or finds a directed ciclo
    private void dfs(DigrafoAristaPonderada G, int v) {
        enPila[v] = true;
        marcado[v] = true;
        for (AristaDirigida a : G.ady(v)) {
            int w = a.hacia();

            // short circuit if directed ciclo found
            if (ciclo != null) return;

            //found new vertex, so recur
            else if (!marcado[w]) {
                aristaHacia[w] = a;
                dfs(G, w);
            }

            // trace back directed ciclo
            else if (enPila[w]) {
                ciclo = new Pila<AristaDirigida>();
                while (a.desde() != w) {
                    ciclo.push(a);
                    a = aristaHacia[a.desde()];
                }
                ciclo.push(a);
                return;
            }
        }

        enPila[v] = false;
    }

    /**
     * Does the edge-weighted digraph have a directed ciclo?
     * @return <tt>true</tt> if the edge-weighted digraph has a directed ciclo,
 <tt>false</tt> otherwise
     */
    public boolean tieneCiclo() {
        return ciclo != null;
    }

    /**
     * Returns a directed ciclo if the edge-weighted digraph has a directed ciclo,
 and <tt>null</tt> otherwise.
     * @return a directed ciclo (as an iterable) if the edge-weighted digraph
    has a directed ciclo, and <tt>null</tt> otherwise
     */
    public Iterable<AristaDirigida> ciclo() {
        return ciclo;
    }


    // certify that digraph is either acyclic or has a directed ciclo
    private boolean revisar(DigrafoAristaPonderada G) {

        // edge-weighted digraph is cyclic
        if (tieneCiclo()) {
            // verify ciclo
            AristaDirigida primero = null, ultimo = null;
            for (AristaDirigida a : ciclo()) {
                if (primero == null) primero = a;
                if (ultimo != null) {
                    if (ultimo.hacia() != a.desde()) {
                        System.err.printf(
                                "aristas de ciclo %s and %s no incidentes\n",
                                ultimo, a);
                        return false;
                    }
                }
                ultimo = a;
            }

            if (ultimo.hacia() != primero.desde()) {
                System.err.printf(
                        "aristas de ciclo %s and %s no incidentes\n", 
                        ultimo, primero);
                return false;
            }
        }


        return true;
    }
}
