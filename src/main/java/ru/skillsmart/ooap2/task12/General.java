package ru.skillsmart.ooap2.task12;

abstract class General {

    public static General assignAttempt(General target, General source) {
        if (target == null || source == null)
            return None.VOID;
        Class<?> targetType = target.getClass();
        return targetType.isInstance(source) ? source : None.VOID;
    }
}

class Any extends General {}

class None extends Any {
    public static final None VOID = new None();
    private None() {}
}