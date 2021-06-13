package force;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

import structure.Element;
import util.Pair;

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

    @Test
    void test_react() {
        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        final Pair<Double, Double> p = f.reactAtElement(e);
        then(p.first).isEqualTo(20.0 / 3);
        then(p.second).isEqualTo(7.0 / 3);
    }

    @Test
    void test_moment() {
        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        final Pair<Double, Double> p = f.momentAtElement(e);
        then(p.first).isEqualTo(-4.0);
        then(p.second).isEqualTo(2.0);
    }
}