1. Игровой персонаж содержит класс/специализацию
Персонаж != Маг. Класс/билд можно ресетнуть: воин -> маг -> следопыт.
class Character { Specialization spec; }

2. Сотрудник содержит должность/роль
Не Manager extends Employee. Роль может меняться (developer -> team lead -> architect).
class Employee { JobRole role; }