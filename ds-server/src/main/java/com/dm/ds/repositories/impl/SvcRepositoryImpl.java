package com.dm.ds.repositories.impl;

import com.dm.ds.entity.QSvc;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class SvcRepositoryImpl {

    private final JPAQueryFactory jqf;

    private final QSvc qSvc = QSvc.svc;

    /**
     * 检测指定名称的服务是否存在
     *
     * @param name 待检测的服务的名称
     * @return 存在返回true, 不存在返回false
     */
    public boolean existsByNameIgnoreCaseAndDeletedIsFalse(Long exceptId, String name) {
        BooleanExpression exp = qSvc.name.equalsIgnoreCase(name)
            .and(qSvc.deleted.isFalse());
        if (!Objects.isNull(exceptId)) {
            exp = exp.and(qSvc.id.ne(exceptId));
        }
        return jqf.selectFrom(qSvc)
            .where(exp).limit(1)
            .fetchCount() > 0;
    }
}
