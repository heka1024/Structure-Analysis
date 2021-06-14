package structure;

import algebra.Matrix;
import force.ConcentratedForce;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class StructureTest {
    @Test
    void test_build_global_kmatrix() {
        final Structure s = Structure.of(
                Element.of(5, 90, 1, 1, 1),
                Element.of(9.9, 45, 1, 1, 1),
                Element.of(12, -90, 1, 1, 1)
        );
        final Matrix k = s.getGlobalKMatrix();
        then(k.rowSize()).isEqualTo(12);
        then(k.columnSize()).isEqualTo(12);
    }

    @Test
    void test_build_global_pmatrix() {
        final Structure s = Structure.of(
                Element.of(5, 90, 1, 1, 1, 1, 2),
                Element.of(9.9, 45, 1, 1, 1, 2, 3),
                Element.of(12, -90, 1, 1, 1, 3, 4)
        );
        s.nodeSize = 4;
        final Element e = s.elementList.get(1);
//        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        e.loads.add(f);
//        final Pair<Double, Double> p = f.momentAtElement(e);
//        final Vec v = Vec.of(0.000, 0.000, 0.000, -6.182, 6.182, 7.274, -0.182, 0.182, -0.817, 0.000, 0.000, 0.000);
//        then(s.getGlobalPVector()).isEqualTo(v);
    }

    @Test
    void test_build_kMatrix() {
        final Structure s = Structure.of(
                Element.of(6, 0, 1, 1, 1, 1, 3),
                Element.of(6, 60, 1, 1, 1, 1, 2),
                Element.of(6, -60, 1, 1, 1, 2, 3)
        );
        System.out.println(s.elementList);
        s.nodeSize = 3;
        final Matrix k = s.getGlobalKMatrix();
        then(k.rowSize()).isEqualTo(9);
        then(k.columnSize()).isEqualTo(9);
        System.out.println(k);
    }
}