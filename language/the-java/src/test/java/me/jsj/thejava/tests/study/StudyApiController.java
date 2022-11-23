package me.jsj.thejava.tests.study;

import lombok.RequiredArgsConstructor;
import me.jsj.thejava.tests.domain.Study;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyApiController {

    private final StudyRepository studyRepository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) {
        return studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study Not Found For '" + id + "'"));
    }

    @GetMapping("/study")
    public Study getStudyByName(@RequestParam String name) {
        return studyRepository.findByName(name).get(0);
    }

    @PostMapping("/study")
    public Study createStudyByJson(@RequestBody Study study) {
        return studyRepository.save(study);
    }

    @PostMapping("/study-form")
    public Study createStudyByForm(@ModelAttribute Study study) {
        return studyRepository.save(study);
    }

    @PostMapping("/study-form-response-string")
    public String createStudyByFormResponseString(@ModelAttribute Study study) {
        return "success";
    }
}
