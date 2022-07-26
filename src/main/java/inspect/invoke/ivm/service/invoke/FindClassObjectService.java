package inspect.invoke.ivm.service.invoke;

import inspect.invoke.ivm.annotation.IvmClass;
import inspect.invoke.ivm.exception.IvmClassNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindClassObjectService {

    private final AutowireCapableBeanFactory beanFactory;

    public Object byClassName(String className) {

        DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
        Map<String, Object> beans = dlbf.getBeansWithAnnotation(IvmClass.class);

        return beans.get(className);
    }
}
