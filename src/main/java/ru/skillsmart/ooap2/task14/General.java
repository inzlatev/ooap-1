package ru.skillsmart.ooap2.task14;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class General {
}

@FunctionalInterface
interface Adder<T> {
    T add(T a, T b);
}

// Непустой, иммутабельный вектор значений T с операцией сложения
final class Vector<T extends General> extends General {
    private final List<T> data;
    private final Adder<? super T> adder;

    public Vector(List<T> data, Adder<? super T> adder) {
        this.data = List.copyOf(Objects.requireNonNull(data, "data"));
        this.adder = Objects.requireNonNull(adder, "adder");
    }

    public static <T extends General> Vector<T> of(List<T> data, Adder<? super T> adder) {
        return new Vector<>(data, adder);
    }

    public int size() {
        return data.size();
    }

    public T get(int i) {
        return data.get(i);
    }

    public List<T> asList() {
        return data;
    }

    // Покомпонентное сложение. Если длины различаются — возвращаем null (по условию)
    public Vector<T> plus(Vector<T> other) {
        if (other == null || this.size() != other.size()) return null;

        List<T> res = new ArrayList<>(size());
        for (int i = 0; i < size(); i++) {
            T a = this.data.get(i);
            T b = other.data.get(i);
            @SuppressWarnings("unchecked")
            T sum = (T) adder.add(a, b);
            res.add(sum);
        }
        return new Vector<>(res, this.adder);
    }

    public static <T extends General> Adder<Vector<T>> adderOf(Adder<? super T> elemAdder) {
        return (v1, v2) -> {
            if (v1 == null || v2 == null || v1.size() != v2.size()) return null;
            List<T> out = new ArrayList<>(v1.size());
            for (int i = 0; i < v1.size(); i++) {
                @SuppressWarnings("unchecked")
                T sum = (T) elemAdder.add(v1.get(i), v2.get(i));
                out.add(sum);
            }
            return new Vector<>(out, elemAdder);
        };
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

// Пример для целого значения (обёртка над int)
final class Int extends General {
    final int value;

    Int(int v) {
        this.value = v;
    }

    static final Adder<Int> ADDER = (a, b) -> new Int(a.value + b.value);

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Int i) && i.value == value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}

// Пример для десятичного числа
final class Dec extends General {
    final BigDecimal value;

    Dec(BigDecimal v) {
        this.value = v;
    }

    static final Adder<Dec> ADDER = (a, b) -> new Dec(a.value.add(b.value));

    @Override
    public String toString() {
        return value.toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Dec d) && d.value.compareTo(value) == 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}


// Демонстрация и проверка Vector<Vector<Vector<T>>>
class Demo {
    public static void main(String[] args) {
        // Векторы Int
        Vector<Int> a = Vector.of(List.of(new Int(1), new Int(2), new Int(3)), Int.ADDER);
        Vector<Int> b = Vector.of(List.of(new Int(10), new Int(20), new Int(30)), Int.ADDER);
        System.out.println("a + b = " + a.plus(b)); // [11, 22, 33]

        // Вложенный уровень: Vector<Vector<Int>>
        Adder<Vector<Int>> vIntAdder = Vector.adderOf(Int.ADDER);
        Vector<Vector<Int>> m1 = Vector.of(List.of(
                Vector.of(List.of(new Int(1), new Int(1)), Int.ADDER),
                Vector.of(List.of(new Int(2), new Int(2)), Int.ADDER)
        ), vIntAdder);

        Vector<Vector<Int>> m2 = Vector.of(List.of(
                Vector.of(List.of(new Int(10), new Int(10)), Int.ADDER),
                Vector.of(List.of(new Int(20), new Int(20)), Int.ADDER)
        ), vIntAdder);

        System.out.println("m1 + m2 = " + m1.plus(m2)); // [[11, 11], [22, 22]]

        // Три уровня: Vector<Vector<Vector<Int>>>
        Adder<Vector<Vector<Int>>> vvIntAdder = Vector.adderOf(vIntAdder);
        Vector<Vector<Vector<Int>>> t1 = Vector.of(List.of(m1, m2), vvIntAdder);
        Vector<Vector<Vector<Int>>> t2 = Vector.of(List.of(m2, m1), vvIntAdder);

        System.out.println("t1 + t2 = " + t1.plus(t2));
        // Ожидаемо: поэлементные суммы на каждом уровне; длины совпадают — не null

        // Пример с Dec (BigDecimal) — другой скаляр
        Vector<Dec> d1 = Vector.of(List.of(new Dec(new BigDecimal("1.5")), new Dec(new BigDecimal("2.25"))), Dec.ADDER);
        Vector<Dec> d2 = Vector.of(List.of(new Dec(new BigDecimal("0.5")), new Dec(new BigDecimal("0.75"))), Dec.ADDER);
        System.out.println("d1 + d2 = " + d1.plus(d2)); // [2.0, 3.00]

        // Разные длины → null
        Vector<Int> bad = Vector.of(List.of(new Int(1), new Int(2)), Int.ADDER);
        System.out.println("a + bad = " + a.plus(bad)); // null
    }
}