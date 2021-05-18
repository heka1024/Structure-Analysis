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
}
