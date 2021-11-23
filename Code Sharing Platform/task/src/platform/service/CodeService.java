package platform.service;

import platform.model.Code;
import java.util.List;

public interface CodeService {

    Code getSnippet(String uuid);

    String addSnippet(Code code);

    List<Code> latestSnippets();
}
