package algebra;

public interface Algebraic<E extends Algebraic<?>> {
    E add(E other);
    E subtract(E other);
    E multiplication(E other);
    E multiplication(Double constant);
}
