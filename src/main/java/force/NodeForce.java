package force;

import algebra.Vec;

public class NodeForce {
    int atNode;
    double magnitude;

    public NodeForce(int atNode, double magnitude) {
        this.atNode = atNode;
        this.magnitude = magnitude;
    }

    public static NodeForce of(int atNode, double magnitude) {
        return new NodeForce(atNode, magnitude);
    }

    public Vec forceVec(int nodeSize) {
        final Vec u = Vec.ofSize(3 * nodeSize);
        u.set((atNode - 1) * 3 + 1, magnitude);
        return u;
    }
}
