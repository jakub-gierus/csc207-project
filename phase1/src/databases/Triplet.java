package databases;

/*
    Utility triple value tuple class inspired by this StackOverflow answer: https://stackoverflow.com/a/6010861
    Used for the purposes of loading user data from CSVs.
 */
public class Triplet<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    /**
     * Utility triple value tuple class used for loading user data from CSVs.
     * @param first piece of data
     * @param second piece of data
     * @param third piece of data
     */
    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }
    public V getThird() { return third; }
}