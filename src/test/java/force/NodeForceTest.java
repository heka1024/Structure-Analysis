package force;

import algebra.Vec;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class NodeForceTest {
    @Test
    void test_force_vector() {
        final NodeForce nf = NodeForce.of(3, 50.0);
        final Vec u = nf.forceVec(5);
        then(u.size()).isEqualTo(15);
        then(u).isEqualTo(Vec.of(0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 50.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000, 0.000));
        then(u.get(7)).isEqualTo(50.0);
    }

}