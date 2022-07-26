package inspect.invoke.ivm.controller;

import inspect.invoke.ivm.service.ivmui.IvmUIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ShowController {

    private final IvmUIService ivmUIService;

    @GetMapping("/IVM-UI")
    public ModelAndView show() {
        return ivmUIService.getTemplate();
    }
}
