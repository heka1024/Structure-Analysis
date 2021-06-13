package structure;

import algebra.Matrix;
import algebra.Vec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Structure {
    public Structure(List<Element> elementList) {
        this.elementList = elementList;
        this.globalKMatrix = buildGlobalK();
    }

    List<Element> elementList;
    final private Matrix globalKMatrix;

    public static Structure of(Element... args) {
        return new Structure(Arrays.asList(args));
    }

    public Matrix getGlobalKMatrix() {
        return globalKMatrix;
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
}
