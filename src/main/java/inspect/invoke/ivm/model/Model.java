package inspect.invoke.ivm.model;

import inspect.invoke.ivm.interfaces.IvmObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Model implements IvmObject {

    private String nome;
    private int idade;

    @Override
    public Object getDefaultIvmObject() {
        return Model.builder().nome("acoplax").idade(999).build();
    }
}
