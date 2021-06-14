package structure;

import algebra.Matrix;
import algebra.Vec;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Structure {
    public Structure(List<Element> elementList) {
        this.elementList = elementList;
    }

    List<Element> elementList;
    public int nodeSize;
//    List<Node> nodes;
    Matrix globalKMatrix;
    Vec globalPVector, globalDVector;

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
        for (int i = 0; i < n; i++) {
            final Element cur = elementList.get(i);
//            System.out.println(Collections.unmodifiableList(elementList));
//            if (cur.node == null) {
//                final Matrix current = elementList
//                        .get(i)
//                        .getTransformedMatrix()
//                        .ofOffset(
//                                3 * i,
//                                3 * i,
//                                3 * (n - 1 - i),
//                                3 * (n - 1 - i)
//                        );
//                pnew = pnew.add(current);
//            } else {
            System.out.println(cur.getSplited(nodeSize));
            System.out.println("--------------------------");
                pnew = pnew.add(cur.getSplited(nodeSize));
//            }
        }
        return pnew;
    }

    private Vec buildGlobalP() {
        final int n = nodeSize;
        Vec pnew = Vec.ofSize(3 * n);
        for (Element element : elementList) {
            final Vec current = element.splitedVec(n);
            pnew = pnew.add(current);
        }
        return pnew;
    }

    public Vec getGlobalDVector() {
        if (globalDVector == null) {
            return buildGlobalDVector();
        }
        return globalDVector;
    }

    private Vec buildGlobalDVector() {
        Objects.requireNonNull(globalKMatrix, "k matrix should be non null");
        Objects.requireNonNull(globalPVector, "p vector should be non null");
        globalDVector = globalKMatrix.solve(globalPVector);
        return globalDVector;
    }
}
