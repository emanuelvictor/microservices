package com.emanuelvictor.api.functional.flowcreator.application.resources;

import com.emanuelvictor.api.functional.flowcreator.application.resources.generic.AbstractResource;
import com.emanuelvictor.api.functional.flowcreator.domain.entities.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/questions")
public class QuestionResource extends AbstractResource<Question, Long> {
}
