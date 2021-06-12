package structure;

import algebra.Vec;
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

}