package algebra;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

public class VecTest {
    @Test
    void construct_vec_well() {
        final Vec u = Vec.of(1.0, 2.0, 3.0);
        then(u.size()).isEqualTo(3);
        then(u.get(0)).isEqualTo(1.0);
        then(u.get(1)).isEqualTo(2.0);
        then(u.get(2)).isEqualTo(3.0);
    }

    @Test
    void ofOffsetTest() {
        final Vec u = Vec.of(1.0, 2.0, 3.0).ofOffset(2);
        then(u.size()).isEqualTo(5);
        then(u.get(0)).isEqualTo(0.0);
        then(u.get(1)).isEqualTo(0.0);
        then(u.get(2)).isEqualTo(1.0);
        then(u.get(3)).isEqualTo(2.0);
        then(u.get(4)).isEqualTo(3.0);
    }

    @Test
    void ofOffsetTestWithSize() {
        final Vec u = Vec.of(1.0, 2.0, 3.0).ofOffset(2, 1);
        then(u.size()).isEqualTo(6);
        then(u.get(0)).isEqualTo(0.0);
        then(u.get(1)).isEqualTo(0.0);
        then(u.get(2)).isEqualTo(1.0);
        then(u.get(3)).isEqualTo(2.0);
        then(u.get(4)).isEqualTo(3.0);
        then(u.get(5)).isEqualTo(0.0);
    }

    @Test
    void add_well() {
        final Vec u = Vec.of(1.0, 2.0, 3.0);
        final Vec v = Vec.of(2.0, 3.0, -4.0);
        final Vec z = u.add(v);
        then(z.size()).isEqualTo(3);
        then(z.get(0)).isEqualTo(3.0);
        then(z.get(1)).isEqualTo(5.0);
        then(z.get(2)).isEqualTo(-1.0);
    }

    @Test
    void subtract_well() {
        final Vec u = Vec.of(1.0, 2.0, 3.0);
        final Vec v = Vec.of(2.0, 3.0, -4.0);
        final Vec z = u.subtract(v);
        then(z.size()).isEqualTo(3);
        then(z.get(0)).isEqualTo(-1.0);
        then(z.get(1)).isEqualTo(-1.0);
        then(z.get(2)).isEqualTo(7.0);
    }

    @Test
    void test_fold() {
        final Vec u = Vec.of(1.0, 2.0, 3.0, 4.0, 5.0);
        then(u.fold(0.0, Double::sum)).isEqualTo(15.0);
    }

    @Test
    void test_reduce() {
        final Vec u = Vec.of(1.0, 2.0, 3.0, 4.0, 5.0);
        then(u.reduce((a, i) -> 2 * a + i)).isEqualTo(57);
    }

    @Test
    void test_dot_product() {
        final Vec u = Vec.of(1.0, 2.0, 3.0);
        final Vec v = Vec.of(3.0, 1.5, 1.0);

        then(u.dot(v)).isEqualTo(9.0);
    }

    @Test
    void test_equals() {
        final Vec u = Vec.of(1.0, 2.0);
        final Vec v = Vec.of(1.0, 0.0).add(Vec.of(0.0, 2.0));
        then(u.equals(v)).isTrue();
    }

    @Test
    void test_unit_vector() {
        final Vec e1 = Vec.of(1.0, 0.0);
        final Vec e3 = Vec.of(0.0, 0.0, 1.0);
        then(Vec.Unit(2, 1)).isEqualTo(e1);
        then(Vec.Unit(3, 3)).isEqualTo(e3);
    }

    @Test
    void test_construct_with_size() {
        final Vec u = Vec.ofSize(3);
        then(u).isEqualTo(Vec.of(0.0, 0.0, 0.0));
    }

    @Test
    void test_subvector_without_limit() {
        final Vec u = Vec.of(0.0, 1.0, 2.0, 3.0, 4.0);
        then(u.subVector(0)).isEqualTo(u);
        then(u.subVector(2)).isEqualTo(Vec.of(2.0, 3.0, 4.0));
    }

    @Test
    void test_subvector_with_limit_and_offset() {
        final Vec u = Vec.of(0.0, 1.0, 2.0, 3.0, 4.0);
        then(u.subVector(2, 2)).isEqualTo(Vec.of(2.0, 3.0));
    }
}
