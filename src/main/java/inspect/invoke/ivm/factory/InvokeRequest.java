package inspect.invoke.ivm.factory;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InvokeRequest {
    private String classLocation;
    private String methodName;
    private List<InvokeArg> args = new ArrayList<>();
}
