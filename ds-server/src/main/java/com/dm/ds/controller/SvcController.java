package com.dm.ds.controller;

import com.dm.ds.converter.SvcConverter;
import com.dm.ds.dto.SqlMetaData;
import com.dm.ds.dto.SvcDto;
import com.dm.ds.entity.Svc;
import com.dm.ds.exception.DataNotExistException;
import com.dm.ds.services.SvcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping({"/svcs", "/svc"})
@Slf4j
@RequiredArgsConstructor
public class SvcController {

    private final SvcService svcService;

    private final SvcConverter svcConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SvcDto save(@Validated({SvcDto.New.class}) @RequestBody SvcDto svc) {
        return svcConverter.toDto(svcService.save(svc));
    }

    @GetMapping("{id}")
    public SvcDto findById(@PathVariable("id") Long id) {
        return svcService.findById(id)
                .map(svcConverter::toDto)
                .orElseThrow(DataNotExistException::new);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public SvcDto update(@PathVariable("id") Long id,
                         @Validated({SvcDto.Update.class}) @RequestBody SvcDto svc) {
        return svcConverter.toDto(svcService.update(id, svc));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        svcService.deleteById(id);
    }

    @GetMapping(params = {"draw"})
    public Page<SvcDto> list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "label", required = false) Set<String> label,
            @PageableDefault Pageable pageable) {
        return svcService.list(search, label, pageable).map(svcConverter::toTableResultDto);
    }

    @PostMapping("meta")
    public SqlMetaData metas(@RequestBody SvcDto svc) throws SQLException {
        return svcService.getMeta(svc).orElseThrow(DataNotExistException::new);
    }

    @GetMapping("meta/{id}")
    public SqlMetaData metas(@PathVariable("id") Svc svc) throws SQLException {
        return svcService.getMeta(svc.getConnection(), svc.getSql());
    }
}
