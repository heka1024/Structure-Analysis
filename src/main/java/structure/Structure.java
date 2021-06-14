package structure;

import algebra.Matrix;
import algebra.Vec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Structure {
    public Structure(List<Element> elementList) {
        this.elementList = elementList;
        this.globalKMatrix = buildGlobalK();
    }

    List<Element> elementList;
    final Matrix globalKMatrix;
    Vec globalPVector, globalDVector;

    public static Structure of(Element... args) {
        return new Structure(Arrays.asList(args));
    }

    public Matrix getGlobalKMatrix() {
        return globalKMatrix;
    }

    public Vec getGlobalPVector() {
        if (globalPVector == null) {
            return globalPVector = buildGlobalP();
        }
        return globalPVector;
    }

    private Matrix buildGlobalK() {
        final int n = elementList.size();
        Matrix pnew = Matrix.ofSize(3 *(n + 1), 3 * (n + 1));
        for (int i = 0; i < n; i++) {
            System.out.println(Collections.unmodifiableList(elementList));
            final Matrix current = elementList
               .get(i)
               .getTransformedMatrix()
               .ofOffset(
                       3 * i,
                       3 * i,
                       3 * (n - 1 - i),
                       3 * (n - 1 - i)
               );
            pnew = pnew.add(current);
        }
        return pnew;
    }

    private Vec buildGlobalP() {
        final int n = elementList.size();
        Vec pnew = Vec.ofSize(3 *(n + 1));
        for (int i = 0; i < n; i++) {
            final Vec current = elementList
                    .get(i)
                    .getTransformedPVector()
                    .ofOffset(
                            3 * i,
                            3 * (n - 1 - i)
                    );
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
