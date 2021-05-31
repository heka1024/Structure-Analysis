package force;

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
        return u(x) * q;
    }

    @Override
    public double magnitude() {
        return q;
    }

    private double u(double x) {
        if (x >= this.x) {
            return 1;
        } else {
            return -1;
        }
    }
}
