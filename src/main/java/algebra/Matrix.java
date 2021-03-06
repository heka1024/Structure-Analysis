package algebra;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Matrix extends ArrayList<Vec> implements Algebraic<Matrix> {
    @Serial
    private static final long serialVersionUID = -8728464115127424576L;

    public static Matrix of(Vec... rows) {
        final Matrix pnew = new Matrix();
        pnew.addAll(Arrays.asList(rows));
        return pnew;
    }

    public static Matrix ofSize(int rows, int cols) {
        final Matrix pnew = new Matrix();
        for (int i = 0; i < rows; i++) {
            pnew.add(Vec.ofSize(cols));
        }
        return pnew;
    }

    public static Matrix Identity(int size) {
        final Matrix pnew = of();
        for (int i = 0; i < size; i++) {
            pnew.add(Vec.Unit(size, i + 1));
        }
        return pnew;
    }

    public Matrix ofOffset(int rows, int cols) {
        final Matrix mat = Matrix.ofSize(rowSize() + rows, columnSize() + cols);
        for (int i = 0; i < rowSize(); i++) {
            final Vec row = get(i).ofOffset(cols);
            mat.set(i + rows, row);
        }
        return mat;
    }

    public Matrix ofOffset(int rows, int cols, int rows2, int cols2) {
        final Matrix mat = Matrix.ofSize(rowSize() + rows + rows2, columnSize() + cols + cols2);
        for (int i = 0; i < rowSize(); i++) {
            final Vec row = get(i).ofOffset(cols, cols2);
            mat.set(i + rows, row);
        }
        return mat;
    }

    public Matrix subMatrix(int skipRow, int skipColumn, int limitRow, int limitColumn) {
        final Matrix m = Matrix.of();
        for (int i = skipRow; i < skipRow + limitRow; i++) {
            m.add(get(i).subVector(skipColumn, limitColumn));
        }
        return m;
    }

    public Matrix subMatrix(int skipRow, int skipColumn) {
        return subMatrix(skipRow, skipColumn, rowSize() - skipRow, columnSize() - skipColumn);
    }

    private static Matrix SmallTransform(double angle) {
        return Matrix.of(
                Vec.of(cos(angle), -sin(angle), 0.0),
                Vec.of(sin(angle), cos(angle), 0.0),
                Vec.of(0.0, 0.0, 1.0)
        );
    }

    public static Matrix Transform(double angle) {
        return SmallTransform(angle).ofOffset(0, 0, 3, 3)
               .add(SmallTransform(angle).ofOffset(3, 3));
    }

    public int rowSize() {
        return size();
    }

    public int columnSize() {
        return get(0).size();
    }

    private Matrix map(UnaryOperator<Vec> fn) {
        final Matrix pnew = of();
        for (Vec row : this) {
            pnew.add(fn.apply(row));
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
        return map(other.transpose()::multiplication);
    }

    @Override
    public Matrix multiplication(Double constant) {
        return map(row -> row.multiplication(constant));
    }

    public Vec multiplication(Vec other) {
        final Vec u = Vec.of();
        for (Vec row : this) {
            u.add(row.dot(other));
        }
        return u;
    }

    public Vec column(int index) {
        final Vec pnew = Vec.of();
        for (Vec row : this) {
            pnew.add(row.get(index));
        }
        return pnew;
    }

    public ArrayList<Vec> columns() {
        return transpose();
    }

    public Matrix transpose() {
        final Matrix cs = of();
        for (int i = 0; i < columnSize(); i++) {
            cs.add(column(i));
        }
        return cs;
    }

    public Vec solve(Vec b) {
        final Matrix mat = (Matrix) clone();
        final Vec x = (Vec) b.clone();
        for (int k = 0; k < mat.columnSize() - 1; k++) {
            // pivoting
            final int pivotIndex = mat.findLargestRowOf(k, k);
            mat.swapRow(k, pivotIndex);
            x.swap(k, pivotIndex);

            // construct echelon form
            final Double pivot = mat.get(k).get(k);
            for (int j = k + 1; j < mat.rowSize(); j++) {
                final Double constant = mat.get(j).get(k) / pivot;
                mat.set(j, mat.get(j).subtract(mat.get(k).multiplication(constant)));
                x.set(j, x.get(j) - x.get(k) * constant);
            }
        }

        // back substitution
        final Vec ans = Vec.ofSize(size());
        for (int i = mat.size() - 1; i >= 0; i--) {
            final Vec constants = (Vec) mat.get(i).clone();
            constants.set(i, 0.0);
            ans.set(i, (x.get(i) - constants.dot(ans)) / mat.get(i).get(i));
        }
        return ans;
    }

    public Matrix inverse() {
        final Matrix mat = (Matrix) clone(), ret = of();
        for (int i = 0; i < size(); i++) {
            ret.add(mat.solve(Vec.Unit(mat.size(), i + 1)));
        }
        return ret.transpose();
    }

    private int findLargestRowOf(int col) {
        return findLargestRowOf(col, 0);
    }

    private int findLargestRowOf(int col, int offset) {
        int largest = offset;
        for (int i = offset; i < rowSize(); i++) {
            if (get(i).get(col) > get(largest).get(col)) {
                largest = i;
            }
        }
        return largest;
    }

    private void swapRow(int i, int j) {
        final Vec tmp = get(i);
        set(i, get(j));
        set(j, tmp);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowSize() - 1; i++) {
            sb.append(get(i));
            sb.append('\n');
        }
        sb.append(get(rowSize() - 1));
        return sb.toString();
    }

    public Matrix pop(int r, int c) {
        final Matrix m = Matrix.of();
        for (int i = 0; i < rowSize(); i++) {
            if (i != r) {
                m.add(get(i).pop(c));
            }
        }
        return m;
    }

    public Matrix pop(int i) {
        return pop(i, i);
    }
}
