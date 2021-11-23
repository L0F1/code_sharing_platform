package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import platform.service.CodeService;

import java.util.List;

@Controller
@RequestMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
public class WebController {

    private final CodeService codeService;

    @Autowired
    public WebController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("{uuid}")
    public ModelAndView getCodeHtml(@PathVariable String uuid) {
        return new ModelAndView("code_page")
                .addObject("codes", List.of(codeService.getSnippet(uuid)))
                .addObject("title", "Code");
    }

    @GetMapping("/new")
    public ModelAndView addCodeHtml() {
        return new ModelAndView("set_code_page");
    }

    @GetMapping("/latest")
    public ModelAndView latestCodeHtml() {
        return new ModelAndView("code_page")
                .addObject("codes", codeService.latestSnippets())
                .addObject("title", "Latest");
    }
}
