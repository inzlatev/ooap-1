Динамическое связывание в данном примере состоит в том, что метод testDrive знает только про интерфейс Car, а JVM во время выполнения сама вызывает нужную реализацию start() в зависимости от фактического типа машины.

```java
interface Car {
    void start();
}

class GasCar implements Car {
    @Override public void start() {
        System.out.println("Крутится стартер, мотор заводится громко");
    }
}

class ElectricCar implements Car {
    @Override public void start() {
        System.out.println("Тихий старт мотора");
    }
}

public class Demo {
    public static void main(String[] args) {
        Car c1 = new GasCar();      // тип ссылки Car, объект — GasCar
        Car c2 = new ElectricCar(); // тип ссылки Car, объект — ElectricCar

        testDrive(c1); // в рантайме вызовется GasCar.start()
        testDrive(c2); // в рантайме вызовется ElectricCar.start()
    }

    static void testDrive(Car car) {
        car.start(); // динамическое связывание (late binding)
    }
}
```
