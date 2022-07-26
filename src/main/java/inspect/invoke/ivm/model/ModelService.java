package inspect.invoke.ivm.model;

import inspect.invoke.ivm.annotation.IvmClass;
import inspect.invoke.ivm.annotation.IvmMethod;
import org.springframework.stereotype.Service;

@Service
@IvmClass
public class ModelService {

    @IvmMethod(name = "testService")
    public void testService(Model model) {
        model.setIdade(999);
    }
}
