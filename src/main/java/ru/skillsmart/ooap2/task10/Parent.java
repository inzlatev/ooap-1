package ru.skillsmart.ooap2.task10;

/**
 * Запрет переопределения методов в потомках в Java реализуется через ключевое слово final, как показано в примере ниже.
 */

public class Parent {

    final String toString(int intToStr) {
        return String.valueOf(intToStr);
    }
}

class Child extends Parent {

    // Ошибка 'Cannot override; overridden method is final'
    @Override
    String toString(int intToStr) {
        return "Child " + super.toString();
    }
}