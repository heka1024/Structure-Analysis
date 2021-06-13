package force;

import structure.Element;
import util.Pair;

public interface Force {
    double value(double x);
    double shearForce(double x);
    double magnitude();
    Pair<Double, Double> reactAtElement(Element e);
    Pair<Double, Double> momentAtElement(Element e);
}
