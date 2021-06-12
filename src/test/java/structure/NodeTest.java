package structure;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    @Test
    void test_get_tan_between_node() {
        final Node a = Node.of(0.0, 0.0);
        final Node b = Node.of(1.0, 3.0);
        final double rad = a.betweenAngle(b);

        then(rad).isCloseTo(1.249, Offset.offset(0.001));
    }
}