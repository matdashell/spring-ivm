package inspect.invoke.ivm.controller;

import inspect.invoke.ivm.service.invoke.ReflectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Configuration
@RestController
@RequiredArgsConstructor
public class InvokeController {

    private final ReflectService reflectService;

    @GetMapping("/invoke/{className}/{methodName}")
    public Object invokeControllerMethod(
            @Nullable @RequestBody List<Object> json,
            @PathVariable String className,
            @PathVariable String methodName) {
        return reflectService.reflect(json, className, methodName);
    }
}
