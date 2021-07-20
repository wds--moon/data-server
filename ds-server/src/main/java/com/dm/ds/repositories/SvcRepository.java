package com.dm.ds.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.dm.ds.entity.Svc;

import java.util.Optional;

 public interface SvcRepository extends JpaRepository<Svc, Long>, QuerydslPredicateExecutor<Svc> {
     Optional<Svc> findByNameAndDeletedIsFalse(String name);

    /**
     * 检测指定名称的服务是否存在。
     *
     * @param exceptId 要排除的ID
     * @param name     要检测的名称
     * @return 如果存在返回true, 如果不存在返回false
     */
     boolean existsByNameIgnoreCaseAndDeletedIsFalse(Long exceptId, String name);

     Page<Svc> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String search,
            Pageable pageable);

    @Query("select distinct l from Svc s,IN (s.labels) l")
     Page<String> findLabels(Pageable pageable);
}
