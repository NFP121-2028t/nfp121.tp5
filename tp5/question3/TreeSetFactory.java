package question3;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetFactory<T extends Comparable<T>> implements Factory<Set<T>> {
    public Set<T> create() {
        return new TreeSet<>();
    }
}