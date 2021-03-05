package org.diwayou.fsm.util;

import org.squirrelframework.foundation.component.SquirrelProvider;
import org.squirrelframework.foundation.fsm.DotVisitor;
import org.squirrelframework.foundation.fsm.StateMachine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author gaopeng 2021/1/19
 */
public class ExportUtil {

    public static void toSvg(StateMachine<?,?,?,?> stateMachine, String filename) {
        DotVisitor visitor = SquirrelProvider.getInstance().newInstance(DotVisitor.class);
        stateMachine.accept(visitor);
        visitor.convertDotFile(filename);

        try {
            Process process = Runtime.getRuntime().exec(String.format("dot -Tsvg %1$s.dot -o %1$s.svg", filename));
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Files.deleteIfExists(Paths.get(filename + ".dot"));
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
