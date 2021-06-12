package structure;

import algebra.Matrix;
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
        System.out.println(k);
    }

}