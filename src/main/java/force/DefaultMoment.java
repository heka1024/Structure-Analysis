package force;

public class DefaultMoment implements Moment {
    double m, x;

    public DefaultMoment(double m, double x) {
        this.m = m;
        this.x = x;
    }

    public static DefaultMoment of(double m, double x) {
        return new DefaultMoment(m, x);
    }

    @Override
    public double magnitude() {
        return m;
    }

    @Override
    public double bodyMoment(double x) {
        return u(x) * magnitude();
    }

    private double u(double x) {
        if (x >= this.x) {
            return 1;
        } else {
            return -1;
        }
    }
}
