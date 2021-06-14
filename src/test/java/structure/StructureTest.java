package structure;

import algebra.Matrix;
import force.ConcentratedForce;
import util.Pair;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

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
                Element.of(5, 90, 1, 1, 1),
                Element.of(9.9, 45, 1, 1, 1),
                Element.of(12, -90, 1, 1, 1)
        );
        final Element e = s.elementList.get(1);
//        final Element e = Element.of(3, 0, 1, 1, 1);
        final ConcentratedForce f = ConcentratedForce.of(9, 1);
        e.loads.add(f);
//        final Pair<Double, Double> p = f.momentAtElement(e);
        System.out.println(s.getGlobalPVector());
    }

}