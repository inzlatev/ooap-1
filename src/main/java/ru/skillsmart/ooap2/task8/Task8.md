В Java обобщения (generics) инвариантны по умолчанию. Вариантность выражается в месте использования через wildcard’ы:
```
? extends T — ковариантность (источник/producer),
? super T — контравариантность (приёмник/consumer)
```

Ковариантность:

```java
class Animal {
}

class Cat extends Animal {
}

List<Cat> cats = new ArrayList<>();
List<Animal> animals = cats; // не сработает, т.к. generics в Java инвариантны и List<Cat> не является подтипом List<Animal>
List<? extends Animal> animals = cats; // правильное использование ковариантности
```

Контравариантность:

```java
import java.util.ArrayList;

class Animal {
}

class Cat extends Animal {
}

List<Animal> animals = new ArrayList<>();
List<Cat> cats = animals; // также не сработает
List<? super Cat> cats = animals; // правильное использование контравариантности
```
