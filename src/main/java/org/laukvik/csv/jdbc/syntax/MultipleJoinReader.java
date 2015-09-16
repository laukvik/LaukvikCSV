package org.laukvik.csv.jdbc.syntax;

import java.util.ArrayList;
import java.util.List;
import org.laukvik.csv.jdbc.Join;

public class MultipleJoinReader extends GroupReader implements JoinReaderListener {

    CrossJoinReader cross;
    InnerJoinReader inner;
    LeftOuterJoinReader left;
    RightOuterJoinReader right;
    NaturalJoinReader natural;
    FullOuterJoinReader outer;

    List<JoinReaderListener> listeners;

    public MultipleJoinReader() {
        listeners = new ArrayList<>();
        cross = new CrossJoinReader();
        natural = new NaturalJoinReader();
        inner = new InnerJoinReader();
        left = new LeftOuterJoinReader();
        right = new RightOuterJoinReader();
        outer = new FullOuterJoinReader();
        cross.addJoinReaderListener(this);
        add(new Either(cross, inner, left, right, outer, natural));
    }

    public void found(Join join) {
        fireTableFound(join);
    }

    public String getPurpose() {
        return "Consumes all joins in the SQL";
    }

    public JoinReaderListener addJoinListener(JoinReaderListener listener) {
        listeners.add(listener);
        return listener;
    }

    public void removeJoinListener(JoinReaderListener listener) {
        listeners.remove(listener);
    }

    public void fireTableFound(Join join) {
        for (JoinReaderListener l : listeners) {
            l.found(join);
        }
    }

}