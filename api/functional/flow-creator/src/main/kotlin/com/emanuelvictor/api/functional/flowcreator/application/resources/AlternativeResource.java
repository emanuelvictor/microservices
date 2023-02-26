package com.emanuelvictor.api.functional.flowcreator.application.resources;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.alternative.AbstractAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.services.AlternativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("alternatives")
public class AlternativeResource {

    private final AlternativeService alternativeService;

    /**
     * @param defaultFilter String
     * @return Page<AbstractAlternative>
     */
    @GetMapping
    public Page<AbstractAlternative> listByFilters(final String defaultFilter, final boolean onlyRoot, final Pageable pageable) {
        return alternativeService.listByFilters(defaultFilter, onlyRoot, pageable);
    }
}
