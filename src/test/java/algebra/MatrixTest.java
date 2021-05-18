package algebra;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

public class MatrixTest {
    @Test
    void construct_well_with_vector() {
        final Matrix a = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        );
        then(a.size()).isEqualTo(2);
        then(a.rowSize()).isEqualTo(2);
        then(a.columnSize()).isEqualTo(3);
    }

    @Test
    void test_matrix_addition() {
        // given
        final Matrix m1 = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        );
        final Matrix m2 = Matrix.of(
                Vec.of(2.0, 2.0, 3.0),
                Vec.of(2.0, 0.0, 4.0)
        );
        final Matrix result = Matrix.of(
                Vec.of(3.0, 4.0, 6.0),
                Vec.of(4.0, 3.0, 8.0)
        );

        // when
        final Matrix m3 = m1.add(m2);

        // then
        then(m3.rowSize()).isEqualTo(2);
        then(m3.columnSize()).isEqualTo(3);
        then(m3).isEqualTo(result);
    }
}
