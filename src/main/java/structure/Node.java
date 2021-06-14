package structure;

public class Node {
    final private double x, y;
    int dofOfX = 0, dofOfY = 0, dofOfR = 0;

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Node(double x, double y, int dofOfX, int dofOfY, int dofOfR) {
        this.x = x;
        this.y = y;
        this.dofOfX = dofOfX;
        this.dofOfY = dofOfY;
        this.dofOfR = dofOfR;
    }

    public static Node of(double x, double y) {
        return new Node(x, y);
    }

    public static Node of(int dofOfX, int dofOfY, int dofOfR) {
        final Node n = new Node(0, 0);
        n.dofOfX = dofOfX;
        n.dofOfY = dofOfY;
        n.dofOfR = dofOfR;
        return n;
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

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", dofOfX=" + dofOfX +
                ", dofOfY=" + dofOfY +
                ", dofOfR=" + dofOfR +
                '}';
    }
}
