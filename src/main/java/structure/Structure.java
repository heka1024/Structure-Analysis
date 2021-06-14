package structure;

import algebra.Matrix;
import algebra.Vec;
import force.NodeForce;
import util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Structure {
    public Structure(List<Element> elementList) {
        this.elementList = elementList;
    }

    List<Element> elementList;
    List<NodeForce> nodeForces = new ArrayList<>();
    public int nodeSize;
    List<Node> nodes = new ArrayList<>();
    List<Integer> xs = new ArrayList<>();
    Matrix globalKMatrix;
    Vec globalPVector, globalDVector = Vec.of();

    public static Structure of(Element... args) {
        return new Structure(Arrays.asList(args));
    }

    public Matrix getGlobalKMatrix() {
        if (globalKMatrix == null) {
            return globalKMatrix = buildGlobalK();
        }
        return globalKMatrix;
    }

    public Vec getGlobalPVector() {
        if (globalPVector == null) {
            return globalPVector = buildGlobalP();
        }
        return globalPVector;
    }

    private Matrix buildGlobalK() {
        final int n = nodeSize;
        Matrix pnew = Matrix.ofSize(3 * n, 3 * n);
        for (Element cur : elementList) {
            pnew = pnew.add(cur.getSplited(nodeSize));
        }
        return pnew;
    }

    private Vec buildGlobalP() {
        final int n = nodeSize;
        Vec pnew = Vec.ofSize(3 * n);
        for (NodeForce nf : nodeForces) {
            pnew = pnew.add(nf.forceVec(n));
        }
        for (Element element : elementList) {
            final Vec current = element.splitedVec(n);
            pnew = pnew.subtract(current);
        }
        return pnew;
    }

    public void distributeForce() {
        for (Element e : elementList) {
            e.buildLocalForce(getGlobalDVector());
        }
    }

    public Vec getGlobalDVector() {
        if (globalDVector == null || globalDVector.size() == 0) {
            return buildGlobalDVector();
        }
        return globalDVector;
    }

    public Vec buildGlobalDVector() {
        Objects.requireNonNull(globalKMatrix, "k matrix should be non null");
        Objects.requireNonNull(globalPVector, "p vector should be non null");
        final Pair<Matrix, Vec> p = filterMatrix();
        System.out.println("---------------- abbc -------------");
        System.out.println(p);
        final Vec partialD = p.first.solve(p.second);
        System.out.println("------ partial d -----");
        System.out.println(partialD);

        int cnt = 0;
        System.out.println(xs);
        for (final int x : xs) {
            if (x == 0) {
                globalDVector.add(0.0);
            } else {
                globalDVector.add(partialD.get(cnt));
                cnt++;
                System.out.println(cnt);
            }
        }

        return globalDVector;
    }

    public Pair<Matrix, Vec> filterMatrix() {
        xs = new ArrayList<>();
        for (Node node : nodes) {
            xs.add(node.dofOfX);
            xs.add(node.dofOfY);
            xs.add(node.dofOfR);
        }
        final Matrix m = Matrix.of();
        final Vec vector = Vec.of();
        for (int i = 0; i < getGlobalKMatrix().size(); i++) {
            if (xs.get(i) == 1) {
                final Vec pnew = Vec.of();
                for (int j = 0; j < getGlobalKMatrix().get(i).size(); j++) {
                    if (xs.get(j) == 1) {
                        pnew.add(getGlobalKMatrix().get(i).get(j));
                    }
                }
                m.add(pnew);
                vector.add(getGlobalPVector().get(i));
            }
        }
        return Pair.of(m, vector);
    }
}
