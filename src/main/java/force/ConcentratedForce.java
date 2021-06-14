package force;

import structure.Element;
import util.Pair;

import static java.lang.Math.pow;

public class ConcentratedForce implements Force {
    final double q, x;
    static final double eps = 0.0000000001;

    public ConcentratedForce(double q, double x) {
        this.q = q;
        this.x = x;
    }

    public static ConcentratedForce of(double q, double x) {
        return new ConcentratedForce(q, x);
    }

    @Override
    public double value(double x) {
        if (Math.abs(x - this.x) <= eps) {
            return q;
        }
        return 0;
    }

    @Override
    public double shearForce(double x) {
        if (x > this.x) {
            return q;
        }
        return 0;
    }

    @Override
    public double magnitude() {
        return q;
    }

    @Override
    public Pair<Double, Double> reactAtElement(Element e) {
        final double a = x, b = e.l - x;
        return Pair.of(
                reactHelper(a, b) / pow(e.l, 3),
                reactHelper(b, a) / pow(e.l, 3)
        );
    }

    @Override
    public Pair<Double, Double> momentAtElement(Element e) {
        final double a = x, b = e.l - x;
        return Pair.of(
                momentHelper(a, b) / pow(e.l, 2),
                -momentHelper(b, a) / pow(e.l, 2)
        );
    }

    private double reactHelper(double a, double b) {
        return pow(b, 2) * (3 * a + b) * q;
    }

    private double momentHelper(double a, double b) {
        return a * pow(b, 2) * q;
    }

    private double u(double x) {
        if (x >= this.x) {
            return 1;
        } else {
            return -1;
        }
    }
}
