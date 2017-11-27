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
public class Bolsa<Item> implements Iterable{
    private int N; // numero de elementos en la bolsa
    private Nodo<Item> primero; //inicio de la bolsa
    
    private static class Nodo<Item>{
        private Item item;
        private Nodo<Item> prox;
    }
    
    public Bolsa(){
        primero = null;
        N = 0;
    }
    
    public boolean estaVacio() {
        return primero == null;
    }

    
    public int tamanno() {
        return N;
    }

    
    public void agregar(Item item) {
        Nodo<Item> anteriorPrimero = primero;
        primero = new Nodo<Item>();
        primero.item = item;
        primero.prox = anteriorPrimero;
        N++;
    }


    
    public Iterator<Item> iterator()  {
        return new IteradorDeLista<Item>(primero);  
    }

    
    private class IteradorDeLista<Item> implements Iterator<Item> {
        private Nodo<Item> actual;

        public IteradorDeLista(Nodo<Item> primero) {
            actual = primero;
        }

        public boolean hasNext()  { return actual != null;                    }
        public void remove()      { throw new UnsupportedOperationException();}

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = actual.item;
            actual = actual.prox; 
            return item;
        }
    }
}
