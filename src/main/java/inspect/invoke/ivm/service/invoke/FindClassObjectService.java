package inspect.invoke.ivm.service.invoke;

import inspect.invoke.ivm.annotation.IvmClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FindClassObjectService {

    private final AutowireCapableBeanFactory beanFactory;

    public Object byClassName(String className) {

        DefaultListableBeanFactory dlbf = (DefaultListableBeanFactory) beanFactory;
        Map<String, Object> beans = dlbf.getBeansWithAnnotation(IvmClass.class);

        char[] chars = className.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);

        return beans.get(new String(chars));
    }
}
