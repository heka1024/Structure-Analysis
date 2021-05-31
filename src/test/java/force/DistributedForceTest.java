package force;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DistributedForceTest {
    @Test
    void value_test() {
        final DistributedForce f = DistributedForce.of(100, 200, 10, 30);
        then(f.value(10)).isEqualTo(100);
        then(f.value(20)).isEqualTo(150);
        then(f.value(30)).isEqualTo(200);
    }

    @Test
    void test_magnitude() {
        final DistributedForce f = DistributedForce.of(100, 200, 10, 30);
        then(f.magnitude()).isEqualTo(3000);
    }


}