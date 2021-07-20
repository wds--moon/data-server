package com.dm.ds.converter;

import com.dm.ds.collections.Lists;
import com.dm.ds.dto.SvcDto;
import com.dm.ds.dto.SvcInfo;
import com.dm.ds.entity.Svc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static java.util.function.Function.identity;

@Component
@RequiredArgsConstructor
public class SvcConverter implements Converter<Svc, SvcDto> {

    private final DmDataSourceConverter datasourceConverter;

    @Override
    public SvcDto toDto(Svc model) {
        if (Objects.isNull(model)) {
            return null;
        } else {
            SvcDto dto = new SvcDto();
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setOrders(model.getOrders());
            dto.setParameters(model.getParameters());
            dto.setRequiredParameters(model.getRequiredParameters());
            dto.setSql(model.getSql());
            dto.setDescription(model.getDescription());
            dto.setConnectionId(model.getConnection().getId());
            dto.setColumns(model.getColumns());
            dto.setLabels(model.getLabels());
            dto.setVersion(model.getVersion());
            return dto;
        }
    }

    @Override
    public Svc copyProperties(Svc model, SvcDto dto) {
        model.setName(dto.getName());
        model.setSql(dto.getSql());
        model.setOrders(dto.getOrders());
        model.setParameters(dto.getParameters());
        model.setRequiredParameters(dto.getRequiredParameters());
        model.setDescription(dto.getDescription());
        model.setColumns(dto.getColumns());
        model.setLabels(dto.getLabels());
        return model;
    }

    public SvcDto toTableResultDto(Svc model) {
        return Optional.ofNullable(model).map(item -> {
            SvcDto dto = new SvcDto();
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setSql(model.getSql());
            dto.setDescription(model.getDescription());
            dto.setConnectionId(model.getConnection().getId());
            dto.setLabels(model.getLabels());
            return dto;
        }).orElseGet(null);
    }

    public SvcInfo toSvcInfo(Svc svc) {
        SvcInfo info = new SvcInfo();
        info.setConnection(datasourceConverter.toDataSourceProperties(svc.getConnection()));
        info.setId(svc.getId());
        info.setName(svc.getName());
        info.setOrders(Lists.transform(svc.getOrders(), identity()));
        info.setParameters(Lists.transform(svc.getParameters(), identity()));
        info.setSql(svc.getSql());
        return info;
    }
}
