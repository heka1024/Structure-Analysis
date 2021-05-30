package force;

public interface Force {
    double value(double x);
    double shearForce(double x);
    double magnitude();
}
