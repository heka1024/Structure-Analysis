package force;

import algebra.Vec;

public class NodeMoment {
    int atNode;
    double magnitude;

    public NodeMoment(int atNode, double magnitude) {
        this.atNode = atNode;
        this.magnitude = magnitude;
    }

    public static NodeMoment of(int atNode, double magnitude) {
        return new NodeMoment(atNode, magnitude);
    }

    public Vec forceVec(int nodeSize) {
        final Vec u = Vec.ofSize(3 * nodeSize);
        u.set((atNode - 1) * 3 + 2, magnitude);
        return u;
    }
}
