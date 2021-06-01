package util;

public class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A2, B2> Pair<A2, B2> of(A2 first, B2 second) {
        return new Pair<>(first, second);
    }
}
