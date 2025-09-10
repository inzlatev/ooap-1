package ru.skillsmart.ooap2.task15;

// Вариант с атрибутом gender
enum Gender {MALE, FEMALE}

class Person1 {
    private final String name;
    private final Gender gender;

    Person1(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    int vacationDays() { // ветвление логики
        if (gender == Gender.MALE)
            return 20;
        if (gender == Gender.FEMALE)
            return 22;
        return 20;
    }
}

// Вариант без атрибута gender
abstract class Person2 {
    protected final String name;

    Person2(String name) {
        this.name = name;
    }

    abstract int vacationDays();
}

class Male extends Person2 {
    Male(String name) {
        super(name);
    }

    @Override
    int vacationDays() {
        return 20;
    }
}

class Female extends Person2 {
    Female(String name) {
        super(name);
    }

    @Override
    int vacationDays() {
        return 22;
    }
}