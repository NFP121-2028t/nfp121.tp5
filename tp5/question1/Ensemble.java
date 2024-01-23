package question1;

import java.util.*;

public class Ensemble<T> extends AbstractSet<T> {

    protected Vector<T> table = new Vector<T>();
    
    public int size() {
        return table.size();
    }

    public Iterator<T> iterator() {
        return table.iterator();
    }

    public boolean add(T t) {
        if (!contains(t))
            return table.add(t);
        return false;
    }

    public Ensemble<T> union(Ensemble<? extends T> e) {
        Ensemble<T> result = new Ensemble<>();
        result.addAll(this);
        result.addAll(e);
        return result;
    }

    public Ensemble<T> inter(Ensemble<? extends T> e) {
        Ensemble<T> result = new Ensemble<>();
        result.addAll(this);
        result.retainAll(e);
        return result;
    }

    public Ensemble<T> diff(Ensemble<? extends T> e) {
        Ensemble<T> result = new Ensemble<>();
        result.addAll(this);
        result.removeAll(e);
        return result;
    }

    public Ensemble<T> diffSym(Ensemble<? extends T> e) {
        Ensemble<T> result = new Ensemble<>();
        result.addAll(this.union(e));
        result.removeAll(this.inter(e));
        return result;
    }
}