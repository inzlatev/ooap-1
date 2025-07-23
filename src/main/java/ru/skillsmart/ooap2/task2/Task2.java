package ru.skillsmart.ooap2.task2;

// Базовый класс: Транспортное средство
class Vehicle {
    protected int speed;

    public Vehicle(int speed) {
        this.speed = speed;
    }

    // Метод для получения скорости
    public int getSpeed() {
        return speed;
    }

    // Метод для описания транспортного средства
    public String describe() {
        return "Vehicle moving at " + speed + " km/h.";
    }
}

// Расширение класса-родителя: добавление новых возможностей
class ElectricVehicle extends Vehicle {
    private final int batteryLevel;

    public ElectricVehicle(int speed, int batteryLevel) {
        super(speed);
        this.batteryLevel = batteryLevel;
    }

    // Новый метод для получения уровня заряда батареи
    public int getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public String describe() {
        return "Electric vehicle moving at " + speed + " km/h with battery level " + batteryLevel + "%.";
    }
}

// Специализация класса-родителя: уточнение поведения
class Bicycle extends Vehicle {
    public Bicycle(int speed) {
        super(speed);
    }

    @Override
    public String describe() {
        return "Bicycle moving at " + speed + " km/h. It doesn't have an engine and is driven by human power.";
    }
}