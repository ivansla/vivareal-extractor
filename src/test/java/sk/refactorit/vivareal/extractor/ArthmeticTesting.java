package sk.refactorit.vivareal.extractor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArthmeticTesting {

    private static final Logger log = LoggerFactory.getLogger(ArthmeticTesting.class);

    @Test
    void a() {
        log.debug("Result: {}", Math.round(8/7));

    }
}
