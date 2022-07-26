package inspect.invoke.ivm.util;

import inspect.invoke.ivm.consts.Consts;

public class Utils {

    public static boolean isPrimitive (Class<?> aClass) {
        return aClass.isPrimitive() || Consts.PRIMITIVES.contains(aClass);
    }

    public static boolean isNotPrimitive(Class<?> aClass) {
        return !isPrimitive(aClass);
    }

    public static boolean nullOrPrimitive (Object object ,Class<?> aClass) {
        return object == null || isPrimitive(aClass);
    }
}
