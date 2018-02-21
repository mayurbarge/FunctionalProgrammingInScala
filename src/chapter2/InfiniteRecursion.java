package chapter2;

public class InfiniteRecursion {
    public static void main(String[] args) {
        main(new String[]{""}); //StackOverflow error
    }
}
