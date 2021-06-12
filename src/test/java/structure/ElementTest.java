package structure;

import algebra.Matrix;
import algebra.Vec;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

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
        then(e.getkMatrix().get(0)).isEqualTo(Vec.of(0.1, 0.0, 0.0, -0.1, 0.0, 0.0));
    }

}