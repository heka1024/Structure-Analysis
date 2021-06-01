package util;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PairTest {
    @Test
    void test_constructor() {
        final Integer i = 4;
        final String x = "hello";
        Pair<Integer, String> p = Pair.of(i, x);
        then(p.first).isEqualTo(i);
        then(p.second).isEqualTo(x);
    }

}