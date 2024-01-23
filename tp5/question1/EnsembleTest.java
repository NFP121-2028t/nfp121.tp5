package question1;

import java.util.Collections;
import java.util.Arrays;

public class EnsembleTest extends junit.framework.TestCase {
    
    private Ensemble<Integer> ensemble;
    
    public void setUp() {
        ensemble = new Ensemble<>();
    }

    public void test_addElement() {
        assertTrue(ensemble.add(42));
        assertTrue(ensemble.contains(42));
    }

    public void test_addDuplicateElement() {
        assertTrue(ensemble.add(42));
        assertFalse(ensemble.add(42));
        assertEquals(1, ensemble.size());
    }

    public void test_addMultipleElements() {
        assertTrue(ensemble.add(42));
        assertTrue(ensemble.add(17));
        assertTrue(ensemble.add(99));
        assertEquals(3, ensemble.size());
    }

    public void test_addAll() {
        Ensemble<Integer> otherEnsemble = new Ensemble<>();
        otherEnsemble.add(42);
        otherEnsemble.add(17);

        assertTrue(ensemble.addAll(otherEnsemble));
        assertTrue(ensemble.contains(42));
        assertTrue(ensemble.contains(17));
        assertEquals(2, ensemble.size());
    }
    
    
    public void testUnion() {
        question1.Ensemble<Integer> e1, e2;
        e1 = new question1.Ensemble<Integer>();
        assertEquals(true, e1.add(2));
        assertEquals(true, e1.add(3));

        e2 = new question1.Ensemble<Integer>();
        assertEquals(true, e2.add(3));
        assertEquals(true, e2.add(4));

        question1.Ensemble<Integer> union = e1.union(e2);
        assertEquals(3, union.size());
        assertTrue(union.contains(2));
        assertTrue(union.contains(3));
        assertTrue(union.contains(4));
    }
    
    public void testInter() {
        Ensemble<Integer> ensemble1 = new Ensemble<>();
        Collections.addAll(ensemble1, 1 , 2 , 3);
        
        Ensemble<Integer> ensemble2 = new Ensemble<>();
        Collections.addAll(ensemble2, 3, 4, 5);

        Ensemble<Integer> result = ensemble1.inter(ensemble2);
        assertEquals(1, result.size());
        assertTrue(result.contains(3));
    }

    public void testDiff() {
        Ensemble<Integer> ensemble1 = new Ensemble<>();
        Collections.addAll(ensemble1, 1 , 2 , 3);
        
        Ensemble<Integer> ensemble2 = new Ensemble<>();
        Collections.addAll(ensemble2, 3 , 4 , 5);

        Ensemble<Integer> result = ensemble1.diff(ensemble2);
        assertEquals(2, result.size());
        
        assertTrue(result.containsAll(Arrays.asList(1, 2)));
    }

    public void testDiffSym() {
        Ensemble<Integer> ensemble1 = new Ensemble<>();
        Collections.addAll(ensemble1, 1 , 2 , 3);
        
        Ensemble<Integer> ensemble2 = new Ensemble<>();
        Collections.addAll(ensemble2, 3 , 4 , 5);

        Ensemble<Integer> result = ensemble1.diffSym(ensemble2);
        assertEquals(4, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 2, 4, 5)));
    }

    public void test_allJoins() {
        question1.Ensemble<Integer> e1 = new question1.Ensemble<Integer>();
        question1.Ensemble<Integer> e2 = new question1.Ensemble<Integer>();
        Ensemble<Integer> expectedResult = new Ensemble<>();
        
        e1.add(1);
        e1.add(2);
        e1.add(3);
        e1.add(4);
        e1.add(5);
        e1.add(6);
        
        e2.add(1);
        e2.add(7);
        e2.add(3);
        e2.add(8);
        e2.add(5);
        e2.add(9);
        
        expectedResult = new Ensemble<>();
        Collections.addAll(expectedResult, 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9);
        assertEquals(expectedResult, e1.union(e2));
        
        expectedResult = new Ensemble<>();
        Collections.addAll(expectedResult, 1 , 3 , 5);
        assertEquals(expectedResult, e1.inter(e2));
        
        expectedResult = new Ensemble<>();
        Collections.addAll(expectedResult, 2 , 4 , 6);
        assertEquals(expectedResult, e1.diff(e2));
        
        expectedResult = new Ensemble<>();
        Collections.addAll(expectedResult, 2 , 4 , 6 , 7 , 8 , 9);
        assertEquals(expectedResult, e1.diffSym(e2));
    }
}