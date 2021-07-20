package com.dm.ds.services;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface CrudService<Model, Dto, PK> {
    /**
     * 保存一条信息
     *
     * @param dto 要保存的信息
     * @return 保存之后的数据模型
     */
    Model save(@NotNull Dto dto);

    /**
     * 删除一跳信息
     *
     * @param id 要删除的数据的
     */
    void deleteById(@NotNull PK id);

    /**
     * 修改一条信息
     *
     * @param id  要修改的数据的id
     * @param dto 要数据的数据模型
     * @return 修改后的数据实体
     */
    Model update(@NotNull PK id, Dto dto) throws EntityNotFoundException;

    /**
     * 更新一条信息
     *
     * @param id  要更新的数据id
     * @param dto 要更新的数据实体
     * @return 更新后的数据实体
     */
    Model patch(@NotNull PK id, Dto dto);

    /**
     * 获取一条信息
     *
     * @param id 要获取的数据id
     * @return 获取到的数据模型
     */
    Optional<Model> findById(@NotNull PK id);

}
