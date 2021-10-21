package org.diwayou.ffsm.util;

import com.alibaba.cola.statemachine.StateMachine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * @author gaopeng 2021/1/19
 */
public class ExportUtil {

    public static void toSvg(StateMachine<?,?,?> stateMachine, String filename) {
        DotVisitor visitor = new DotVisitor();
        String content = stateMachine.accept(visitor);

        String dotFileName = filename + ".dot";
        try {
            Files.write(Paths.get(dotFileName), Collections.singletonList(content), StandardCharsets.UTF_8);

            Process process = Runtime.getRuntime().exec(String.format("dot -Tsvg %1$s.dot -o %1$s.svg", filename));
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Files.deleteIfExists(Paths.get(dotFileName));
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
