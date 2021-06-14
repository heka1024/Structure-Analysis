package structure;

import algebra.Matrix;
import algebra.Vec;
import force.Force;
import util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.toRadians;

public class Element {
    public Pair<Integer, Integer> node;
    public double l, A, E, I, angle = 0.0;
    final Matrix kMatrix, tMatrix;
    Vec pVector;
    List<Force> loads = new ArrayList<>();

    public Element(double l, double a, double e, double i) {
        this.l = l;
        A = a;
        E = e;
        I = i;
        kMatrix = buildK();
        tMatrix = buildT(angle);
    }

    public Element(double l, double angle, double a, double e, double i) {
        this.l = l;
        A = a;
        E = e;
        I = i;
        this.angle = angle;
        kMatrix = buildK();
        tMatrix = buildT(angle);
    }

    private Matrix buildT(double angle) {
        return Matrix.Transform(toRadians(angle));
    }

    public static Element of(double l, double a, double e, double i) {
        return new Element(l, a, e, i);
    }

    public static Element of(double l, double angle, double a, double e, double i) {
        return new Element(l, angle, a, e, i);
    }

    public static Element of(double l, double angle, double a, double e, double i, int p, int q) {
        final Element el = new Element(l, angle, a, e, i);
        el.node = Pair.of(p, q);
        return el;
    }

    private Matrix buildK() {
        final double a1 = A * E / l;
        final double a2 = 12 * E * I / pow(l, 3);
        final double a3 = 6 * E * I / pow(l, 2);
        final double a4 = 2 * E * I / l;
        final Matrix A1 = Matrix.of(
                Vec.of(a1, 0.0, 0.0),
                Vec.of(0.0, a2, a3),
                Vec.of(0.0, a3, 2 * a4)
        ).ofOffset(0, 0, 3, 3);
        final Matrix A2 = Matrix.of(
                Vec.of(-a1, 0.0, 0.0),
                Vec.of(0.0, -a2, a3),
                Vec.of(0.0, -a3, a4)
        ).ofOffset(0, 3, 3, 0);
        final Matrix A3 = Matrix.of(
                Vec.of(-a1, 0.0, 0.0),
                Vec.of(0.0, -a2, -a3),
                Vec.of(0.0, a3, a4)
        ).ofOffset(3, 0, 0, 3);
        final Matrix A4 = Matrix.of(
                Vec.of(a1, 0.0, 0.0),
                Vec.of(0.0, a2, -a3),
                Vec.of(0.0, -a3, 2 * a4)
        ).ofOffset(3, 3, 0, 0);
        return A1.add(A2).add(A3).add(A4);
    }

    public Matrix getKMatrix() {
        return kMatrix;
    }

    public Matrix getTransformedMatrix() {
        return tMatrix.multiplication(kMatrix).multiplication(tMatrix.transpose());
    }

    public Vec buildP() {
        final Vec u = Vec.ofSize(6);
        for (Force load : loads) {
            final Pair<Double, Double> react = load.reactAtElement(this);
            final Pair<Double, Double> moment = load.momentAtElement(this);
            u.set(1, u.get(1) + react.first);
            u.set(4, u.get(4) + react.second);
            u.set(2, u.get(2) + moment.first);
            u.set(5, u.get(5) + moment.second);
        }
        return pVector = u;
    }

    public Vec getTransformedPVector() {
        if (pVector == null) {
            pVector = buildP();
            return tMatrix.multiplication(pVector);
        }
        return tMatrix.multiplication(pVector);
    }

    public Vec splitedVec(int nodeSize) {
        if (pVector == null) {
            pVector = buildP();
        }
        final int n = nodeSize, p = node.first, q = node.second;
        final Vec u = Vec.ofSize(3 * nodeSize);
        final Vec vec = tMatrix.multiplication(pVector);
        final Vec a = vec.subVector(0, 3);
        final Vec b = vec.subVector(3, 3);
        return u
            .add(a.ofOffset(3 * (p - 1), 3 * (n - p)))
            .add(b.ofOffset(3 * (q - 1), 3 * (n - q)));
    }

    public Matrix getSplited(int nodeSize) {
        Objects.requireNonNull(node, "node is null");
        final int p = node.first, q = node.second;
        final Matrix m = tMatrix.multiplication(kMatrix).multiplication(tMatrix.transpose());
        final Matrix a = m.subMatrix(0, 0, 3, 3);
        final Matrix c = m.subMatrix(0, 3, 3, 3);
        final Matrix b = m.subMatrix(3, 0, 3, 3);
        final Matrix d = m.subMatrix(3, 3, 3, 3);
//        System.out.println(helper(a, nodeSize, p, p));
//        System.out.println("-------------------");
//        System.out.println(helper(b, nodeSize, p, q));
//                System.out.println("-------------------");
//
//        System.out.println(helper(c, nodeSize, q, p));
//                System.out.println("-------------------");
//
//        System.out.println(helper(d, nodeSize, q, q));
//                System.out.println("-------------------");


        return Matrix.ofSize(3 * nodeSize, 3 * nodeSize)
                .add(helper(a, nodeSize, p, p))
                .add(helper(b, nodeSize, p, q))
                .add(helper(c, nodeSize, q, p))
                .add(helper(d, nodeSize, q, q));
    }

    public Matrix helper(Matrix mSmall, int n, int p, int q) {
        return mSmall.ofOffset(
                3 * (q - 1),
                3 * (p - 1),
                3 * (n - q),
                3 * (n - p)
        );
    }



    @Override
    public String toString() {
        return "Element{" +
                "l=" + l +
                ", A=" + A +
                ", E=" + E +
                ", I=" + I +
                ", angle=" + angle +
                ", node=" + node +
                '}';
    }
}
