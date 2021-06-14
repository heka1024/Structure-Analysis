package structure;

import algebra.Vec;
import force.ConcentratedForce;
import force.DistributedForce;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ElementTest {
    @Test
    void test_build_kmatrix() {
        final Element e = Element.of(10, 1, 1, 1);
//        final Matrix m = Matrix.of(
//                Vec.of(0.1, 0.0, 0.0, -0.1, 0, 0),
//                Vec.of(0.1, 0, 0, -0.1, 0, 0),
//                Vec.of(0.1, 0, 0, -0.1, 0, 0),
//                Vec.of(0.1, 0, 0, -0.1, 0, 0),
//                Vec.of(0.1, 0, 0, -0.1, 0, 0),
//                Vec.of(0.1, 0, 0, -0.1, 0, 0),
//        )
        System.out.println(e.kMatrix);
        then(e.getKMatrix().get(0)).isEqualTo(Vec.of(0.1, 0.0, 0.0, -0.1, 0.0, 0.0));
    }

    @Test
    void test_transformed_matrix() {
        final Element e = Element.of(8, 32,1, 1, 1);
        System.out.println(e.getTransformedMatrix());
    }

    @Test
    void test_build_local() {
        final Element e = Element.of(9.9, 45, 1, 1, 1);
        System.out.println(e.getTransformedMatrix());
    }

    @Test
    void test_build_d_vector() {
        final DistributedForce f = DistributedForce.of(0, 6, 0, 10);
        final ConcentratedForce cf = ConcentratedForce.of(8, 5);
        final Element e = Element.of(10, 1, 1, 1);
        e.loads.add(f);
        e.loads.add(cf);
        final Vec d = e.buildP();
        then(d).isEqualTo(Vec.of(0.0, 13.0, -30.0, 0.0, 25.0, 40.0));
    }
}