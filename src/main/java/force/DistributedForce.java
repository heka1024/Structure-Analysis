package force;

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
        return q1 + (q2 - q1) * ((x - x1) / (x2 - x1));
    }

    @Override
    public double shearForce(double x) {
        return 0;
    }

    @Override
    public double magnitude() {
        return (q1 + q2) / 2 * (x2 - x1);
    }
}
