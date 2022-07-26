package inspect.invoke.ivm.service.invoke;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitBeansService {

    private final AutowireCapableBeanFactory beanFactory;

    public void init(Object classObject, Class<?> aClass) {
        List<Field> fields;
        try {
            fields = Arrays.asList(aClass.getDeclaredFields());
        } catch (Exception e) {
            return;
        }
        fields.forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.get(classObject) == null) {
                    NamedBeanHolder<?> namedBeanHolder = beanFactory.resolveNamedBean(field.getType());
                    field.set(classObject, namedBeanHolder.getBeanInstance());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
