package inspect.invoke.ivm.service.ivmui;

import inspect.invoke.ivm.consts.Consts;
import inspect.invoke.ivm.dto.ClassData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IvmUIService {

    private final FindAllAnnotedClassesService findAllAnnotedClassesService;
    private final GetAllClassesData getAllClassesData;

    public ModelAndView getTemplate() {
        ModelAndView modelAndView = new ModelAndView(Consts.INDEX);
        List<Object> classesObjects = findAllAnnotedClassesService.find();
        List<ClassData> classDataList = getAllClassesData.get(classesObjects);
        modelAndView.addObject(Consts.CLASS_DATA_LIST, classDataList);
        return modelAndView;
    }
}
