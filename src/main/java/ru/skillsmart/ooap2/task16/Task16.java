package ru.skillsmart.ooap2.task16;

import java.util.List;

public class Task16 {

    // Полиморфизм
    static class Animal {
        void speak() { System.out.println("???"); }
        String name() { return getClass().getSimpleName(); }
    }
    static class Dog extends Animal {
        @Override void speak() { System.out.println("Гав"); }
    }
    static class Cat extends Animal {
        @Override void speak() { System.out.println("Мяу"); }
    }

    // Ковариантность
    static void printAllNames(List<? extends Animal> animals) {
        for (Animal a : animals) {
            System.out.println(a.name());
        }
    }

    static class Creator {
        Animal create() { return new Animal(); }
    }
    static class DogCreator extends Creator {
        @Override Dog create() { return new Dog(); }
    }

    public static void main(String[] args) {
        // Полиморфный вызов
        Animal x = new Dog();  // полиморфное присваивание: Animal <- Dog
        x.speak();             // вызов Dog.speak()

        // Ковариантный вызов
        List<Dog> dogs = List.of(new Dog(), new Dog());
        List<Cat> cats = List.of(new Cat());
        printAllNames(dogs);
        printAllNames(cats);

        Creator c = new DogCreator();
        Animal created = c.create();           // вернётся Dog как Animal
        System.out.println("created: " + created.name());

        DogCreator dc = new DogCreator();
        Dog dog = dc.create();                 // вернётся Dog как Dog
        System.out.println("dog: " + dog.name());
    }
}
