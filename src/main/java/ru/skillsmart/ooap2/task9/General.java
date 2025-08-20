package ru.skillsmart.ooap2.task9;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Base64;

abstract class General implements Serializable {

    // copyTo: глубокое копирование в существующий объект того же класса
    public final void copyTo(General target) {
        if (target == null || target.getClass() != getClass())
            throw new IllegalArgumentException("target must be same class");
        General clone = this.cloneDeep();
        copyFields(clone, target);
    }

    // cloneDeep: создать новый объект и глубоко скопировать в него текущее состояние
    @SuppressWarnings("unchecked")
    public final <T extends General> T cloneDeep() {
        return (T) fromBase64(toBase64(), getClass());
    }

    // deepEquals: глубокое сравнение (по сериализованной форме)
    public final boolean deepEquals(General other) {
        return other != null && other.getClass() == getClass()
                && this.toBase64().equals(other.toBase64());
    }

    // serialize / deserialize (строка Base64)
    public final String toBase64() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos  = new ObjectOutputStream(baos)) {
            oos.writeObject(this);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }
    public static <T extends General> T fromBase64(String data, Class<?> type) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
             ObjectInputStream ois  = new ObjectInputStream(bais)) {
            Object obj = ois.readObject();
            if (!type.isInstance(obj)) throw new IllegalArgumentException("Bad type");
            @SuppressWarnings("unchecked") T cast = (T) obj;
            return cast;
        } catch (IOException e) { throw new UncheckedIOException(e);
        } catch (ClassNotFoundException e) { throw new RuntimeException(e); }
    }

    // печать
    @Override public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        Class<?> c = getClass(); boolean first = true;
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers())) continue;
                f.setAccessible(true);
                try {
                    if (!first) sb.append(", ");
                    sb.append(f.getName()).append("=").append(f.get(this));
                    first = false;
                } catch (IllegalAccessException ignored) {}
            }
            c = c.getSuperclass();
        }
        return sb.append("}").toString();
    }

    // проверка типа
    public final boolean is(Class<?> t) { return t != null && t.isInstance(this); }

    // реальный тип
    public final Class<?> realType() { return getClass(); }

    // утилита копирования полей ---
    private static void copyFields(Object src, Object dst) {
        Class<?> c = src.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers())) continue;
                f.setAccessible(true);
                try { f.set(dst, f.get(src)); } catch (IllegalAccessException ignored) {}
            }
            c = c.getSuperclass();
        }
    }
}

class Any extends General { }