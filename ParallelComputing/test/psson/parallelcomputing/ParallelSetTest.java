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

import java.util.LinkedList;
import java.util.ListIterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of ParallelSet class
 * @author Andreas Pettersson
 */
public class ParallelSetTest {
    
    private static final int NUM_ATOMS = 1000;
    private static final int NUM_THREADS  = 40;
    
    private LinkedList<ParallelAtom> testAtoms;
    
    public ParallelSetTest() {
        testAtoms = new LinkedList();
    }
    
    public boolean allTestAtomsExecuted() {
        ListIterator myIter = testAtoms.listIterator();
        ParallelAtomImpl myAtom;
        while ( myIter.hasNext() ) {
            myAtom = (ParallelAtomImpl)myIter.next();
            if ( !myAtom.isExecuted() ) {
                return false;
            }
        }        
        return true;
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("\nParallelSet tests");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testAtoms.clear();
        for( int i = 0 ; i < NUM_ATOMS ; i++ ) {
            testAtoms.add( new ParallelAtomImpl() );
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setThreads method, of class ParallelSet.
     */
    @Test
    public void testSetThreads() {
        System.out.println("setThreads");
        int threads = NUM_THREADS;
        ParallelSet instance = new ParallelSet(testAtoms);
        instance.setThreads(threads);
        instance.run();
        assert( this.allTestAtomsExecuted() );
    }

    /**
     * Test of run method, of class ParallelSet.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        ParallelSet instance = new ParallelSet( testAtoms , NUM_THREADS );
        instance.run();
        assert( this.allTestAtomsExecuted() );
    }
    
}
