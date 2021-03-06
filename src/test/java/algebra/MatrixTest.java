package algebra;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;

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
    void test_matrix_with_offset() {
        final Matrix a = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        ).ofOffset(1, 1);
        then(a.rowSize()).isEqualTo(3);
        then(a.columnSize()).isEqualTo(4);
        then(a.get(0)).isEqualTo(Vec.of(0.0, 0.0, 0.0, 0.0));
        then(a.get(1)).isEqualTo(Vec.of(0.0, 1.0, 2.0, 3.0));
        then(a.get(2)).isEqualTo(Vec.of(0.0, 2.0, 3.0, 4.0));
    }

    @Test
    void test_matrix_with_offset_with_size() {
        final Matrix a = Matrix.of(
                Vec.of(1.0, 2.0, 3.0),
                Vec.of(2.0, 3.0, 4.0)
        ).ofOffset(1, 1, 1, 1);
        then(a.rowSize()).isEqualTo(4);
        then(a.columnSize()).isEqualTo(5);
        then(a.get(0)).isEqualTo(Vec.of(0.0, 0.0, 0.0, 0.0, 0.0));
        then(a.get(1)).isEqualTo(Vec.of(0.0, 1.0, 2.0, 3.0, 0.0));
        then(a.get(2)).isEqualTo(Vec.of(0.0, 2.0, 3.0, 4.0, 0.0));
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
    void test_transform() {
        final Matrix m = Matrix.Transform(0.5235); // deg 30
        System.out.println(m);
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

    @Test
    void solve_matrix() {
        final Matrix m = Matrix.Identity(3).multiplication(0.5);
        final Vec u = Vec.of(3.0, 2.0, 1.0);
        Vec x = m.solve(u);
        then(x).isEqualTo(u.multiplication(2.0));
    }

    @Test
    void solve_matrix_2() {
        final Matrix a = Matrix.of(
                Vec.of(3.0, 1.0,   -1.0),
                Vec.of(1.0, 5.0, 3.0),
                Vec.of(-5.0, -8.0, 12.0)
        );
        System.out.println(a.multiplication(a.inverse()));
        final Vec b = Vec.of(10.0, 12.0, 22.0);
        System.out.println(a.solve(b));

    }

    @Test
    void solve_matrix_with_12() {
        final Matrix m = Matrix.of(
                Vec.of(0.188,0.0,-.0375,-0.188,0.0,-0.375,0.0,0.0,0.0,0.0,0.0,0.0),
                Vec.of(0.0,0.25,0.0,0.0,-0.25,0.0,0.0,0.0,0.0,0.0,0.0,0.0),
                Vec.of(-0.375,0.0,1.0,0.375,0.0,0.5,0.0,0.0,0.0,0.0,0.0,0.0),
                Vec.of(-0.188,0.0,0.375,0.438,0.0,0.375,-0.250,0.0,0.0,0.0,0.0,0.0),
                Vec.of(0.0,-0.25,0.0,0.0,0.438,0.375,0.0,-0.188,0.375,0.0,0.0,0.0),
                Vec.of(-0.375,0.0,0.5,0.375,0.375,2.0,0.0,-0.375,0.5,0.0,0.0,0.0),
                Vec.of(0.0,0.0,0.0,-0.25,0.0,0.0,0.438,0.0,0.375,-0.188,0.0,0.375),
                Vec.of(0.0,0.0,0.0,0.0,-0.188,-0.375,0.0,0.438,-0.375,0.0,-0.25,0.0),
                Vec.of(0.0,0.0,0.0,0.0,0.375,0.5,0.375,-0.375,2.0,-0.375,0.0,0.5),
                Vec.of(0.0,0.0,0.0,0.0,0.0,0.0,-0.188,0.0,-0.375,0.188,0.0,-0.375),
                Vec.of(0.0,0.0,0.0,0.0,0.0,0.0,0.0,-0.25,0.0,0.0,0.025,0.0),
                Vec.of(0.0,0.0,0.0,0.0,0.0,0.0,0.375,0.0,0.5,-0.375,0.0,1.0)
        );
        final Vec u = Vec.ofSize(12);
        final Vec x = m.solve(u);
        System.out.println(x);

    }

    @Test
    void test_swap_row() {
        final Matrix m = Matrix.of(
                Vec.of(0.0, 8.0, 2.0),
                Vec.of(3.0, 5.0, 2.0),
                Vec.of(6.0, 2.0, 8.0)
        );
        final Vec u = Vec.of(-7.0, 8.0, 26.0);
        final Vec x = m.solve(u);
        final Vec ans = Vec.of(4.0, -1.0, 0.5);
        then(x).isEqualTo(ans);
    }

    @Test
    void test_inverse_to_identity() {
        final Matrix i = Matrix.Identity(3);
        final Matrix m = i.multiplication(2.0);
        then(i.inverse()).isEqualTo(i);
        then(m.inverse()).isEqualTo(i.multiplication(0.5));
//        then().isEqualTo(i);
    }

    @Test
    void test_inverse() {
        final Matrix m = Matrix.of(
                Vec.of(1.0, 2.0, 4.5),
                Vec.of(1.0, 3.0, 5.5),
                Vec.of(-9.3, -2.0, 1.7)
        );
        final Matrix n = Matrix.of(
                Vec.of(3.0, -2.0),
                Vec.of(-1.0, 1.0)
        );
        System.out.println(m.inverse());
        System.out.println("------------");
        System.out.println(m.inverse().inverse());
        System.out.println("----------------");
        System.out.println(m.inverse().multiplication(m));
//        then(m.inverse()).isEqualTo(n);
//        then().isEqualTo(i);
    }

    @Test
    void test_submatrix_with_limit() {
        final Matrix m = Matrix.of(
                Vec.of(0.0, 8.0, 2.0, -1.0),
                Vec.of(3.0, 5.0, 2.0, -3.0),
                Vec.of(6.0, 2.0, 8.0, -2.0)
        );
        final Matrix n = m.subMatrix(1, 2, 1, 2);
        final Matrix subMat = Matrix.of(
                Vec.of(2.0, -3.0)
        );
        then(n).isEqualTo(subMat);
    }

    @Test
    void test_submatrix() {
        final Matrix m = Matrix.of(
                Vec.of(0.0, 8.0, 2.0),
                Vec.of(3.0, 5.0, 2.0),
                Vec.of(6.0, 2.0, 8.0)
        );
        final Matrix n = m.subMatrix(1, 2);
        then(n.get(0)).isEqualTo(Vec.of(2.0));
        then(n.get(1)).isEqualTo(Vec.of(8.0));
    }

    @Test
    void test_pop() {
        final Matrix m = Matrix.of(
                Vec.of(0.0, 0.1, 0.2, 0.3),
                Vec.of(1.0, 1.1, 1.2, 1.3),
                Vec.of(2.0, 2.1, 2.2, 2.3)
        );
        final Matrix n = m.pop(1, 2);
        final Matrix k = Matrix.of(
                Vec.of(0.0, 0.1, 0.3),
                Vec.of(2.0, 2.1, 2.3)
        );
        then(n).isEqualTo(k);
    }

    @Test
    void test_pop_same_index() {
        final Matrix m = Matrix.of(
                Vec.of(0.0, 0.1, 0.2, 0.3),
                Vec.of(1.0, 1.1, 1.2, 1.3),
                Vec.of(2.0, 2.1, 2.2, 2.3)
        );
        final Matrix n = m.pop(1);
        final Matrix k = Matrix.of(
                Vec.of(0.0, 0.2, 0.3),
                Vec.of(2.0, 2.2, 2.3)
        );
        then(n).isEqualTo(k);
    }
}
