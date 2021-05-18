package algebra;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Matrix extends ArrayList<Vec> implements Algebraic<Matrix> {
    @Serial
    private static final long serialVersionUID = -8728464115127424576L;

    public static Matrix of(Vec... rows) {
        final Matrix pnew = new Matrix();
        pnew.addAll(Arrays.asList(rows));
        return pnew;
    }

    public int rowSize() {
        return size();
    }

    public int columnSize() {
        return get(0).size();
    }

    private Matrix map(UnaryOperator<Vec> fn) {
        final Matrix pnew = of();
        for (int i = 0; i < size(); i++) {
            pnew.add(fn.apply(get(i)));
        }
        return pnew;
    }

    private Matrix map2(Matrix other, BinaryOperator<Vec> fn) {
        final Matrix pnew = of();
        for (int i = 0; i < size(); i++) {
            pnew.add(fn.apply(get(i), other.get(i)));
        }
        return pnew;
    }

    @Override
    public Matrix add(Matrix other) {
        return map2(other, Vec::add);
    }

    @Override
    public Matrix subtract(Matrix other) {
        return map2(other, Vec::subtract);
    }

    @Override
    public Matrix multiplication(Matrix other) {
        return null;
    }

    @Override
    public Matrix multiplication(Double constant) {
        return null;
    }

    public Matrix transpose() {
        return null;
    }

    public Vec column(int index) {
        final Vec pnew = Vec.of();
        for (Vec row : this) {
            pnew.add(row.get(index));
        }
        return pnew;
    }

    public ArrayList<Vec> columns() {
        return null;
//        for (int i = 0; i < columnSize(); i++) {
//            for (Vec row : get(i)) {
//
//            }
//        }
    }
}
