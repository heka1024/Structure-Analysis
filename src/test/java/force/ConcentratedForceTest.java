package force;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class ConcentratedForceTest {
    @Test
    void test_magnitude() {
        final ConcentratedForce f = ConcentratedForce.of(150, 10);
        then(f.magnitude()).isEqualTo(150);
    }

    @Test
    void test_value() {
        final ConcentratedForce f = ConcentratedForce.of(150, 10);
        then(f.value(5)).isEqualTo(0);
        then(f.value(10)).isEqualTo(150);
        then(f.value(12)).isEqualTo(0);
    }

    @Test
    void test_shear_force() {
        final ConcentratedForce f = ConcentratedForce.of(150, 10);
        then(f.shearForce(5)).isEqualTo(-150);
        then(f.shearForce(12)).isEqualTo(150);
    }
}