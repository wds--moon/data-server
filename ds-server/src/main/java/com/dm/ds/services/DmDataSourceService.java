package com.dm.ds.services;

import com.dm.ds.entity.DmDataSource;
import com.dm.ds.dto.DmDataSourceDto;
import com.dm.ds.jdbc.TableMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DmDataSourceService {
    DmDataSource save(DmDataSourceDto connection);

    DmDataSource update(Long id, DmDataSourceDto connection);

    Optional<DmDataSource> findById(Long id);

    Page<DmDataSource> list(String keyword, Pageable pageable);

    List<DmDataSource> listAll();

    List<TableMeta> listTables(Long connection);
}
