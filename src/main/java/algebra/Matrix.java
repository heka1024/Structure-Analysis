package algebra;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix extends ArrayList<Vec> implements Algebraic<Matrix> {
    @Serial
    private static final long serialVersionUID = -8728464115127424576L;

    public static Matrix of(Vec... rows) {
        final Matrix pnew = new Matrix();
        pnew.addAll(Arrays.asList(rows));
        return pnew;
    }

    @Override
    public Matrix add(Matrix other) {
        return null;
    }

    @Override
    public Matrix subtract(Matrix other) {
        return null;
    }

    @Override
    public Matrix multiplication(Matrix other) {
        return null;
    }

    @Override
    public Matrix multiplication(Double constant) {
        return null;
    }
}
