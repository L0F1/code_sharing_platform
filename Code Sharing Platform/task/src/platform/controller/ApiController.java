package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import platform.model.Code;
import platform.service.CodeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    private final CodeService codeService;

    @Autowired
    public ApiController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("{uuid}")
    public Code getCode(@PathVariable String uuid) {
        return codeService.getSnippet(uuid);
    }

    @PostMapping("/new")
    public Map<String, String> setCode(@RequestBody Code code) {
        return Map.of("id", String.valueOf(codeService.addSnippet(code)));
    }

    @GetMapping("/latest")
    public List<Code> latestCode() {
        return codeService.latestSnippets();
    }
}
