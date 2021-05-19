package algebra;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Vec extends ArrayList<Double> implements Algebraic<Vec> {
    @Serial
    private static final long serialVersionUID = 2477584290530884511L;

    public static Vec Unit(int size, int direction) {
        final Double[] elements = new Double[size];
        Arrays.fill(elements, 0.0);
        elements[direction - 1] = 1.0;
        final Vec u = of();
        u.addAll(Arrays.asList(elements));
        return u;
    }

    static Vec of(Double... args) {
        final Vec pnew = new Vec();
        pnew.addAll(Arrays.asList(args));
        return pnew;
    }

    private Vec map(UnaryOperator<Double> fn) {
        final Vec pnew = of();
        for (int i = 0; i < size(); i++) {
            pnew.add(fn.apply(get(i)));
        }
        return pnew;
    }

    private Vec map2(Vec other, BinaryOperator<Double> fn) {
        final Vec pnew = of();
        for (int i = 0; i < size(); i++) {
            pnew.add(fn.apply(get(i), other.get(i)));
        }
        return pnew;
    }

    public <A> A fold(A v, BiFunction<A, Double, A> fn) {
        A acc = v;
        for (Double x : this) {
            acc = fn.apply(acc, x);
        }
        return acc;
    }

    public <A> double reduce(BinaryOperator<Double> fn) {
        Double acc = get(0);
        for (int i = 1; i < size(); i++) {
            acc = fn.apply(acc, get(i));
        }
        return acc;
    }

    public Double dot(Vec other) {
        return multiplication(other).reduce(Double::sum);
    }

    @Override
    public Vec add(Vec other) {
        return map2(other, Double::sum);
    }

    @Override
    public Vec subtract(Vec other) {
        return map2(other, (x, y) -> x - y);
    }

    @Override
    public Vec multiplication(Vec other) {
        return map2(other, (x, y) -> x * y);
    }

    @Override
    public Vec multiplication(Double constant) {
        return map(x -> x * constant);
    }
}
