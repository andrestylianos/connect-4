package connect4.utils;

public class Pair<S, T> {

    public final S first;
    public final T second;

    public Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(this.getClass().isInstance(obj))) {
            return false;
        }

        Pair other = this.getClass().cast(obj);

        if (this.first != other.first) {
            return false;
        }

        if (this.second != other.second) {
            return false;
        }

        return true;

    }
}
