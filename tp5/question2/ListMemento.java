package question2;

import java.util.ArrayList;
import java.util.List;

public class ListMemento {
    private List<String> state;

    public ListMemento(List<String> state) {
        this.state = new ArrayList<>(state);
    }

    public List<String> getState() {
        return state;
    }
}
