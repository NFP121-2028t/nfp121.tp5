package question3;

import java.util.Set;
import java.util.HashSet;

public class HashSetFactory<T> implements Factory<Set<T>> {
    public Set<T> create() {
        return new HashSet<>();
    }
}