package platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import platform.model.Code;
import platform.repository.CodeRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public Code getSnippet(String uuid) {
        Code code = codeRepository.findCodeByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (code.getViews() > 0L) {
            code.setViews(code.getViews()-1);
            if (code.getViews() == 0) {
                codeRepository.deleteById(code.getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else {
                codeRepository.save(code);
            }
        }
        if (code.getTime() > 0L) {
            LocalDateTime deadline = LocalDateTime.parse(code.getDeadline());
            LocalDateTime now = LocalDateTime.now();
            if (deadline.isBefore(now)) {
                codeRepository.deleteById(code.getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            else {
                code.setTime(Duration.between(now, deadline).toSeconds());
                codeRepository.save(code);
            }
        }

        return code;
    }

    @Override
    public String addSnippet(Code code) {
        code.setDate(LocalDateTime.now().format(formatter));
        code.setUuid(UUID.randomUUID().toString());

        if (code.getTime() == null || code.getTime() < 0) {
            code.setTime(0L);
        }
        if (code.getViews() == null || code.getViews() < 0) {
            code.setViews(0L);
        }
        if (code.getTime() > 0) {
            code.setDeadline(LocalDateTime.parse(code.getDate(), formatter)
                    .plusSeconds(code.getTime()).toString());
        }
        if (code.getViews() > 0) {
            code.setViews(code.getViews()+1);
        }

        return codeRepository.save(code).getUuid();
    }

    @Override
    public List<Code> latestSnippets() {
        return codeRepository.findLatest();
    }
}
