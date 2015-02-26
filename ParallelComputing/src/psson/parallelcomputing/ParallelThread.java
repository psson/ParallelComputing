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

import java.util.NoSuchElementException;

/**
 *
 * @author Andreas Pettersson
 */
public class ParallelThread implements Runnable {
    
    ParallelSet mySet;
    ParallelAtom myAtom;
    boolean thingsToDo;

    /**
     * Creates a new ParallelThread and associates it to the supplied ParallelSet
     * 
     * @param parallelSet a ParallelSet
     */
    public ParallelThread(ParallelSet parallelSet) {

        mySet = parallelSet;
        thingsToDo = true;

    }

    /**
     * Executes ParallelAtoms from the associated ParallelSet
     */
    public void run() {

        fetch();

        while(thingsToDo) {

            myAtom.execute();

            fetch();
            
        }
        
    }

    /**
     * This function fetches the next ParallelAtom from the associated ParallelSet
     */
    private void fetch() {

        try {
            myAtom = mySet.getData();
        } catch (NoSuchElementException e) {
            thingsToDo = false;
        }
    }
    
}
