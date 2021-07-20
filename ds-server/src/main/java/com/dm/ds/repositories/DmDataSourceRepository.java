package com.dm.ds.repositories;

import com.dm.ds.entity.DmDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DmDataSourceRepository extends JpaRepository<DmDataSource, Long>, QuerydslPredicateExecutor<DmDataSource> {
}
