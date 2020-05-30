package jdbcExample.db;

public class SingletonExample {

    private static SingletonExample instance = new SingletonExample();


    private SingletonExample() {

    }

    public static SingletonExample getInctance() {
        return instance;
    }
}
