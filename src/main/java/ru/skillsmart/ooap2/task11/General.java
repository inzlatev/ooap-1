package ru.skillsmart.ooap2.task11;

/**
 * В Java нет множественного наследования, поэтому наследника всех классов добавить не удастся.
 * Однако, можно дополнительно обезопасить себя от null, создав объект-синоним пустоты None.
 * Все, что работает с абстракциями уровня Any, сможет полиморфно принимать или реальные объекты, или None.
 */
abstract class General {
}

class Any extends General {
    // Маркер пустоты
    public boolean isVoid() {
        return false;
    }

    public String describe() {
        return getClass().getSimpleName();
    }
}

// Единичный объект-пустота
final class None extends Any {
    public static final None VOID = new None(); // глобальный синглтон

    // запрет внешнего создания
    private None() {
    }

    @Override
    public boolean isVoid() {
        return true;
    }

    @Override
    public String describe() {
        return "None";
    }
}


// Классы-листья
final class Car extends Any {
    private final String model;

    Car(String model) {
        this.model = model;
    }

    @Override
    public String describe() {
        return "Car(" + model + ")";
    }
}

final class User extends Any {
    private final String name;

    User(String name) {
        this.name = name;
    }

    @Override
    public String describe() {
        return "User(" + name + ")";
    }
}

class Main {
    static void process(Any x) {
        if (x.isVoid()) {
            System.out.println("skip: got None");
            return;
        }
        System.out.println("process: " + x.describe());
    }

    public static void main(String[] args) {
        Any v1 = None.VOID;
        Any v2 = new Car("Tesla");
        Any v3 = new User("Bob");

        process(v1); // skip: got None
        process(v2); // process: Car(Tesla)
        process(v3); // process: User(Bob)
    }
}