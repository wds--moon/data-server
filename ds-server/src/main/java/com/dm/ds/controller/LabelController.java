package com.dm.ds.controller;

import com.dm.ds.services.SvcService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"labels"})
@RequiredArgsConstructor
public class LabelController {

    private final SvcService svcService;

    @GetMapping
    public Page<String> findLabels(Pageable pageable) {
        return svcService.listLabels(pageable);
    }
}
