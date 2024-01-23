package question3;

import java.util.Set;
import java.util.Iterator;

public class Tests extends junit.framework.TestCase {

    public void test1(question3.Factory<Set<Integer>> f) throws Exception {
        Set<Integer> set = f.create();
        
        // Ajouter des éléments à l'ensemble
        for (int i = 20; i > 0; i--)
            set.add(i);
            
        // Vérifier la taille de l'ensemble
        assertEquals(20, set.size());

        // Vérifier que les éléments ajoutés sont présents
        for (int i = 1; i < 20; i++)
            assertTrue(set.contains(i));

        // Vérifier l'itération sur l'ensemble
        Iterator<Integer> iter = set.iterator();
        int sum = 0;
        while (iter.hasNext()) {
            sum += iter.next();
        }
        assertEquals(210, sum);  /* 210 = 20 + 19 + 18 + . . . + 3 + 2 + 1 */

        // Ajouter un élément déjà présent (doit retourner false)
        assertFalse(set.add(3));

        // Vérifier que la taille n'a pas changé
        assertEquals(20, set.size());
    }

    public void testCreation() {
        try {
            test1(new TreeSetFactory<Integer>());
            test1(new HashSetFactory<Integer>());
        } catch (NoSuchMethodError e) {
            fail("NoSuchMethodError : " + e.getMessage());
        } catch (Exception e) {
            fail(" exception inattendue : " + e.getMessage());
        }
    }
}