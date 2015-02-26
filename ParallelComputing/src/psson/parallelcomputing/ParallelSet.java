/*
 * The MIT License
 *
 * Copyright 2015 Andreas Pettersson.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package psson.parallelcomputing;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Runs a Collection of ParallelAtoms
 * @author Andreas Pettersson
 */
public class ParallelSet implements Runnable {
    
    public final static int DEFAULT_THREADS = 1;
    
    private LinkedList<ParallelAtom> atoms;
    private ListIterator<ParallelAtom> curPos;
    private int threads;
    
    /**
     * Creates a new ParallelSet using the supplied Collection of ParallelAtoms. The number of threads is set to the number supplied
     * @param c a Collection of ParallelAtoms
     * @param threads the desired number of threads
     */
    public ParallelSet(Collection<ParallelAtom> c, int threads) {

        this.threads = threads;
        atoms = new LinkedList(c);

    }

    /**
     * Creates a new ParallelSet using the supplied Collection of ParallelAtoms. The number of threads is set to DEFAULT_THREADS.
     * 
     * @param c a Collection of ParallelAtoms
     */
    public ParallelSet(Collection<ParallelAtom> c) {

        this(c,DEFAULT_THREADS);
        
    }
    
    /**
     * Returns the next ParallelAtom to be executed.
     * If no atom remains NoSuchElementException is thrown
     * 
     * @return ParallelAtom the next ParallelAtom in the set
     * @throws NoSuchElementException 
     */
    public synchronized ParallelAtom getData() throws NoSuchElementException {

        return curPos.next();

    }

    /**
     * Returns a ListIterator for the atoms of the ParallelSet
     * 
     * @return ListIterator a ListIterator for the atoms of the ParallelSet
     */
    public ListIterator<ParallelAtom> listIterator() {

        return atoms.listIterator();
    }
    
    /**
     * Sets the number of threads to start when running the ParallelSet
     * 
     * @param threads the number of threads to run
     */
    public void setThreads(int threads) {
        
        this.threads = threads;
        
    }

    /**
     * Function executes all ParallelAtoms in the Collection
     */
    @Override
    public void run() {

        curPos = atoms.listIterator();

        Thread[] myThreads = new Thread[threads];

        for(int i = 0;i<threads;i++) {

            myThreads[i] = new Thread(new ParallelThread(this));
            myThreads[i].start();
            
        }

        for(int i=0;i<threads;i++) {
            try {
                myThreads[i].join();
            } catch (InterruptedException ignored) {}
        }

    }
}
