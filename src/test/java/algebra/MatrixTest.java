package algebra;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.ArrayList;
import java.util.Arrays;

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
    void test_column() {
        final Matrix m = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        );
        then(m.column(0)).isEqualTo(Vec.of(1.0, 2.0));
        then(m.column(1)).isEqualTo(Vec.of(2.0, 3.0));
        then(m.column(2)).isEqualTo(Vec.of(3.0, 4.0));
    }

    @Test
    void test_columns() {
        final Matrix m = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        );
        final ArrayList<Vec> cols = new ArrayList<>(
                Arrays.asList(Vec.of(1.0, 2.0), Vec.of(2.0, 3.0), Vec.of(3.0, 4.0))
        );
        then(m.columns()).isEqualTo(cols);
    }

    @Test
    void test_transpose_matrix() {
        final Matrix m = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        );
        final Matrix transposed = Matrix.of(
                Vec.of(1.0, 2.0),
                Vec.of(2.0, 3.0),
                Vec.of(3.0, 4.0)
        );

        then(m.transpose()).isEqualTo(transposed);
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

    @Test
    void test_identity() {
        final Matrix i1 = Matrix.of(
                Vec.of(1.0, 0.0, 0.0),
                Vec.of(0.0, 1.0, 0.0),
                Vec.of(0.0, 0.0, 1.0)
        );
        then(Matrix.Identity(3)).isEqualTo(i1);
    }

    @Test
    void matrix_mult_with_vector() {
        final Matrix i1 = Matrix.of(
                Vec.of(1.0, 0.0, 0.0),
                Vec.of(0.0, 1.0, 0.0),
                Vec.of(0.0, 0.0, 1.0)
        );
        final Vec u = Vec.of(3.0, 2.0, 1.0);
        then(i1.multiplication(u)).isEqualTo(Vec.of(3.0, 0.0, 0.0));
    }

    @Test
    void matrix_mult_with_vector_more_complex() {
        final Matrix i1 = Matrix.of(
                Vec.of(1.0, 0.0, 0.0),
                Vec.of(2.0, 1.0, 0.0),
                Vec.of(3.0, 0.0, 1.0)
        );
        final Vec u = Vec.of(3.0, 2.0, 1.0);
        then(i1.multiplication(u)).isEqualTo(Vec.of(3.0, 8.0, 10.0));
    }

    @Test
    void matrix_mult_with_constant() {
        final Matrix m = Matrix.of(
                Vec.of(1.0, 0.0, 0.0),
                Vec.of(2.0, 1.0, 0.0),
                Vec.of(3.0, 0.0, 1.0)
        );
        final Matrix n = Matrix.of(
                Vec.of(-1.5, -0.0, -0.0),
                Vec.of(-3.0, -1.5, -0.0),
                Vec.of(-4.5, -0.0, -1.5)
        );
        then(m.multiplication(-1.5)).isEqualTo(n);
    }

    @Test
    void matrix_mult() {
        final Matrix m = Matrix.of(
                Vec.of(1.0, 1.0),
                Vec.of(2.0, 3.0)
        );
        final Matrix n = Matrix.of(
                Vec.of(-2.0, 6.0),
                Vec.of(1.0, 5.0)
        );
        final Matrix k = Matrix.of(
                Vec.of(-1.0, 11.0),
                Vec.of(-1.0, 27.0)
        );
        then(m.multiplication(n)).isEqualTo(k);
    }

    @Test
    void constructor_with_size() {
        final Matrix m = Matrix.ofSize(2, 3);
        final Matrix n = Matrix.of(
                Vec.of(0.0, 0.0, 0.0),
                Vec.of(0.0, 0.0, 0.0)
        );
        then(m).isEqualTo(n);
    }
}
