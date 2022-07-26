package inspect.invoke.ivm.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ClassData {
    private String name;
    private List<String> methods;
}
