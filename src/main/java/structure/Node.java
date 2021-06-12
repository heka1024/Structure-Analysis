package structure;

public class Node {
    final private double x, y;
    int dofOfX = 0, dofOfY = 0, dofOfR = 0;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Node of(double x, double y) {
        return new Node(x, y);
    }

    public static Node of(double x, double y, int dofX, int dofY, int dofR) {
        final Node node = new Node(x, y);
        node.dofOfR = dofR;
        node.dofOfX = dofX;
        node.dofOfY = dofY;
        return node;
    }

    public double betweenAngle(Node other) {
        return Math.atan2(other.y - y, other.x - x);

    }
}
