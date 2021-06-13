package force;

import structure.Element;
import util.Pair;

import static java.lang.Math.pow;

public class DistributedForce implements Force {
    final double q1, q2, x1, x2;

    public DistributedForce(double q1, double q2, double x1, double x2) {
        this.q1 = q1;
        this.q2 = q2;
        this.x1 = x1;
        this.x2 = x2;
    }

    public static DistributedForce of(double q1, double q2, double x1, double x2) {
        return new DistributedForce(q1, q2, x1, x2);
    }

    @Override
    public double value(double x) {
        if (x < x1 || x > x2) {
            return 0;
        } else {
            return q1 + (q2 - q1) * ((x - x1) / (x2 - x1));
        }
    }

    @Override
    public double shearForce(double x) {
        if (x < x1) {
            return 0;
        } else if (x > x2) {
            return magnitude();
        } else {
            return (value(x) + q1) / 2 * (x - x1);
        }
    }

    @Override
    public double magnitude() {
        return (q1 + q2) / 2 * (x2 - x1);
    }

    @Override
    public Pair<Double, Double> reactAtElement(Element e) {
        final double l1 = x1, l2 = e.l - x2, L = e.l;
        double v = 1 + l2 / (L - l1) + pow(l2, 2) / pow(L - l1, 2);
        final double fsb = q1 * pow(L - l1, 3) / (20 * pow(L, 3)) * (
                (7 * L + 8 * l1)
                - l2 * (3 * L + 2 * l1) / (L - l1)
                    * v
                + 2 * pow(l2, 4) / pow(L - l1, 3)
        ) + q2 * pow(L - l1, 3) / (20 * pow(L, 3)) * (
                (3 * L + 2 * l1) * v
                - pow(l2, 3) / pow(L - l1, 2) * (
                        2 + (15 * L - 8 * l2) / (L - l1)
                )
        );
        final double fse = (q1 + q2) * (e.l - l1 - l2) - fsb;
        return Pair.of(fsb, fse);
    }

    @Override
    public Pair<Double, Double> momentAtElement(Element e) {
        return null;
    }
}
