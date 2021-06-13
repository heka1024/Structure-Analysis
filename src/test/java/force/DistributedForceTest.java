package force;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import structure.Element;
import util.Pair;

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

    @Test
    void test_shearforce() {
        final DistributedForce f = DistributedForce.of(100, 200, 10, 30);
        then(f.shearForce(5)).isEqualTo(0);
        then(f.shearForce(35)).isEqualTo(3000);
        then(f.shearForce(20)).isEqualTo(1250);
    }

    @Test
    void test_react() {
        final DistributedForce f = DistributedForce.of(0, 6, 0, 10);
        final Element e = Element.of(10, 1, 1, 1);
        final Pair<Double, Double> p = f.reactAtElement(e);
        then(p.first + p.second).isEqualTo(30);
        then(p.first).isEqualTo(9.0);
        then(p.second).isEqualTo(21.0);
    }

    @Test
    void test_moment() {
        final DistributedForce f = DistributedForce.of(0, 6, 0, 10);
        final Element e = Element.of(10, 1, 1, 1);
        final Pair<Double, Double> p = f.momentAtElement(e);
        then(p.first).isEqualTo(-20.0);
        then(p.second).isEqualTo(30.0);
    }
}