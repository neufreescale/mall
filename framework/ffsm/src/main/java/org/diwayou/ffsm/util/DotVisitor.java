package org.diwayou.ffsm.util;

import com.alibaba.cola.statemachine.State;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.Transition;
import com.alibaba.cola.statemachine.Visitor;

/**
 * @author gaopeng 2021/10/21
 */
public class DotVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> visitable) {
        StringBuilder buffer = new StringBuilder();
        writeLine(buffer, "digraph {\ncompound=true;");
        writeLine(buffer, "subgraph cluster_StateMachine {\nlabel=\"" + visitable.getClass().getName() + "\";");

        return buffer.toString();
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> visitable) {
        return "}}";
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> visitable) {
        StringBuilder buffer = new StringBuilder();

        String stateId = visitable.getId().toString();
        writeLine(buffer, stateId + " [label=\"" + stateId + "\"];");

        for (Transition transition : visitable.getTransitions()) {
            String realStart = transition.getSource().toString();
            String realEnd = transition.getTarget().toString();
            String edgeLabel = transition.getEvent().toString();

            writeLine(buffer, "\n" + realStart + " -> " + realEnd + " [" + " label=\"" + edgeLabel + "\"];");
        }

        return buffer.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> visitable) {
        return "";
    }

    private void writeLine(StringBuilder buffer, final String msg) {
        buffer.append(msg).append("\n");
    }
}
