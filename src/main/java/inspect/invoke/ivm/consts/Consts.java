package inspect.invoke.ivm.consts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Consts {
    public static final String CLASS_DATA_LIST = "classDataList";
    public static final String INDEX = "index";
    public static String DEFAULT_METHOD_NAME_IMPL = "getDefaultIvmObject";
    public static List<Class<?>> PRIMITIVES = new ArrayList<>(Arrays.asList(String.class, Double.class, Integer.class, BigDecimal.class, Float.class));
}
