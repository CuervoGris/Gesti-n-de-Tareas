/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.Librerias.EstructasBase;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author assi
 */
public class Cola<Tipo> implements Iterable<Tipo> {
    private int N;               // number of elements on queue
    private Nodo<Tipo> primero;    // beginning of queue
    private Nodo<Tipo> ultimo;     // end of queue

    // helper linked list class
    private static class Nodo<Item> {
        private Item item;
        private Nodo<Item> prox;
    }

    /**
     * Initializes an empty queue.
     */
    public Cola() {
        primero = null;
        ultimo  = null;
        N = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
     */
    public boolean estaVacia() {
        return primero == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int tamanno() {
        return N;     
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Tipo enFrente() {
        if (estaVacia()) throw new NoSuchElementException("Cola vacía");
        return primero.item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param  item the item to add
     */
    public void entrarACola(Tipo item) {
        Nodo<Tipo> antiguoUltimo = ultimo;
        ultimo = new Nodo<Tipo>();
        ultimo.item = item;
        ultimo.prox = null;
        if (estaVacia()) primero = ultimo;
        else           antiguoUltimo.prox = ultimo;
        N++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public Tipo salirDeCola() {
        if (estaVacia()) throw new NoSuchElementException("Cola vacía");
        Tipo item = primero.item;
        primero = primero.prox;
        N--;
        if (estaVacia()) ultimo = null;   // to avoid loitering
        return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Tipo item : this)
            s.append(item + " ");
        return s.toString();
    } 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Tipo> iterator()  {
        return new IteradorDeLista<Tipo>(primero);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class IteradorDeLista<Item> implements Iterator<Item> {
        private Nodo<Item> actual;

        public IteradorDeLista(Nodo<Item> primero) {
            actual = primero;
        }

        public boolean hasNext()  { return actual != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = actual.item;
            actual = actual.prox; 
            return item;
        }
    }
}
