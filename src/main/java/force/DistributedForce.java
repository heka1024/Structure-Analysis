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
        final double l1 = x1, l2 = e.l - x2, L = e.l, k = L - l1;
        double v = 1 + l2 / k + pow(l2, 2) / pow(k, 2);

        final double fsb = q1 * pow(k, 3) / (20 * pow(L, 3)) * (
                (7 * L + 8 * l1)
                - l2 * (3 * L + 2 * l1) / k
                    * v
                + 2 * pow(l2, 4) / pow(k, 3)
        ) + q2 * pow(k, 3) / (20 * pow(L, 3)) * (
                (3 * L + 2 * l1) * v
                - pow(l2, 3) / pow(k, 2) * (
                        2 + (15 * L - 8 * l2) / k
                )
        );
        final double fse = (q1 + q2) / 2 * (e.l - l1 - l2) - fsb;
        return Pair.of(fsb, fse);
    }

    @Override
    public Pair<Double, Double> momentAtElement(Element e) {
        final double l1 = x1, l2 = e.l - x2, L = e.l, k = L - l1;
        final double v = 1 + l2 / k + pow(l2, 2) / pow(k, 2);
        final double left = 3 * (L + 4 * l1)
                            - l2 * (2 * L + 3 * l1) / k * v
                            + 3 * pow(l2, 4) / pow(k, 3);
        final double right = (2 * L + 3 * l1) * v
            - 2 * pow(l2, 3) / pow(k, 2) * (1 + (5 * L - 4 * l2) / k);
        final double fmb = q1 * pow(k, 3) / (60 * pow(L, 2)) * left
            + q2 * pow(k, 3) / (60 * pow(L, 2)) * right;
        final double fme = (k - l2) / 6 * (
                q1 * (-2 * L + 2 * l1 - l2) - q2 * (L - l1 + 2 * l2)
        ) - fmb - L * reactAtElement(e).first; // - FSb(L)
        return Pair.of(fmb, fme);
    }
}
