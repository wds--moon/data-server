package com.dm.ds.controller;

import com.dm.ds.collections.Lists;
import com.dm.ds.converter.DmDataSourceConverter;
import com.dm.ds.dto.DmDataSourceDto;
import com.dm.ds.exception.DataNotExistException;
import com.dm.ds.jdbc.CheckResult;
import com.dm.ds.jdbc.ConnectionUtils;
import com.dm.ds.jdbc.TableMeta;
import com.dm.ds.provider.DataSourceProperties;
import com.dm.ds.provider.DataSourceProvider;
import com.dm.ds.provider.DataSourceProviderHolder;
import com.dm.ds.services.DmDataSourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping({"/connections", "/dataSources"})
public class DmDataSourceController {

    private final DmDataSourceService dataSourceService;
    private final DmDataSourceConverter dataSourceConverter;

    public DmDataSourceController(DmDataSourceService dataSourceService, DmDataSourceConverter cnnConverter) {
        this.dataSourceService = dataSourceService;
        this.dataSourceConverter = cnnConverter;
    }


    /**
     * 新增一条连接信息
     *
     * @param connection 连接信息
     * @return 生成的连接信息
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DmDataSourceDto save(@Validated(Default.class) @RequestBody DmDataSourceDto connection) {
        return dataSourceConverter.toDto(dataSourceService.save(connection));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DmDataSourceDto update(@PathVariable("id") Long id,
                                  @RequestBody @Validated(Default.class) DmDataSourceDto connection) {
        return dataSourceConverter.toDto(dataSourceService.update(id, connection));
    }

    @GetMapping("{id}")
    public DmDataSourceDto get(@PathVariable("id") Long id) {
        return dataSourceService.findById(id).map(dataSourceConverter::toDto).orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = {"page", "size"})
    public Page<DmDataSourceDto> list(
        @RequestParam(value = "keyword", required = false) String keyword,
        @PageableDefault Pageable pageable) {
        return dataSourceService.list(keyword, pageable).map(dataSourceConverter::toSimpleDto);
    }

    @GetMapping(params = {"!page", "!size"})
    public List<DmDataSourceDto> list() {
        return Lists.transform(dataSourceService.listAll(), dataSourceConverter::toDto);
    }

    @GetMapping("{cnn}/state")
    public CheckResult state(@PathVariable("cnn") Long id) {
        return dataSourceService.findById(id)
            .map(dataSourceConverter::toDataSourceProperties)
            .map(this::checkState)
            .orElseThrow(DataNotExistException::new);
    }

    @PostMapping(value = "state")
    public CheckResult state(@RequestBody DmDataSourceDto cnn) {
        return checkState(dataSourceConverter.toDataSourceProperties(cnn));
    }

    private CheckResult checkState(DataSourceProperties properties) {
        DataSourceProvider provider = DataSourceProviderHolder.getProvider(properties.getDbType());
        return ConnectionUtils.checkState(
            provider.getUrl(properties),
            provider.getDriverClassName(),
            properties.getUsername(),
            properties.getPassword());
    }

    @GetMapping("{connection}/tables")
    public List<TableMeta> listTables(@PathVariable("connection") Long connection) {
        return dataSourceService.listTables(connection);
    }
}
