package inspect.invoke.ivm.service.ivmui;

import inspect.invoke.ivm.annotation.IvmClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllAnnotedClassesService {

    private final AutowireCapableBeanFactory beanFactory;

    public List<Object> find() {
        DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
        Map<String, Object> beans = dlbf.getBeansWithAnnotation(IvmClass.class);
        return beans.keySet()
                .stream()
                .map(beans::get)
                .collect(Collectors.toList());
    }
}
