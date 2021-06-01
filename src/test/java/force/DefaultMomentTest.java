package force;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultMomentTest {
    @Test
    void test_get_magnitude() {
        final double size = 5.0, x = 10.0;
        Moment m = DefaultMoment.of(size, x);

        then(m.magnitude()).isEqualTo(size);
        then(m.bodyMoment(0.0)).isEqualTo(-size);
        then(m.bodyMoment(20.0)).isEqualTo(size);
    }

}