package ru.skillsmart.ooap2.task1;

// Пример использования наследования, композиции и полиморфизма

// Базовый (родительский) класс
class Animal {
    // Метод, который может быть переопределен в наследниках
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

// Наследник класса Animal (is-a отношение: Dog является Animal)
class Dog extends Animal {
    // Переопределяем метод makeSound (пример полиморфизма)
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }
}

// Ещё один наследник
class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

// Класс, использующий композицию (has-a отношение)
// Дом содержит животное, но не является животным.
class House {
    private final Animal pet;  // композиция: дом "имеет" животное

    public House(Animal pet) {
        this.pet = pet;
    }

    public void makePetSound() {
        pet.makeSound(); // Полиморфизм: вызовется метод конкретного типа (Dog или Cat)
    }
}

// Тестовый класс с точкой входа
class Main {
    public static void main(String[] args) {
        // Полиморфизм: переменная типа Animal может ссылаться на Dog или Cat
        Animal dog = new Dog();
        Animal cat = new Cat();

        dog.makeSound(); // Вывод: Dog barks
        cat.makeSound(); // Вывод: Cat meows

        // Композиция: создаем дом с собакой
        House houseWithDog = new House(dog);
        houseWithDog.makePetSound(); // Вывод: Dog barks

        // Композиция: создаем дом с кошкой
        House houseWithCat = new House(cat);
        houseWithCat.makePetSound(); // Вывод: Cat meows
    }
}