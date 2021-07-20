package com.dm.ds.services;

import com.dm.ds.entity.DmDataSource;
import com.dm.ds.dto.SqlMetaData;
import com.dm.ds.dto.SvcDto;
import com.dm.ds.dto.SvcInfo;
import com.dm.ds.entity.Svc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

/**
 * 服务配置服务
 *
 * @author LiDong
 */
public interface SvcService extends CrudService<Svc, SvcDto, Long> {

    /**
     * 根据服务名称获取服务
     *
     * @param name 服务名称
     * @return 获取到的数据模型
     */
    Optional<SvcInfo> findByName(String name);

    /**
     * 获取服务的元数据信息
     *
     * @param svc 数据服务定义
     * @return SQL元数据信息
     * @throws SQLException 发生SQL错误时抛出该异常
     */
    Optional<SqlMetaData> getMeta(SvcDto svc) throws SQLException;

    /**
     * 从指定连接中获取指定sql的查询元数据信息
     *
     * @param cnn 指定的连接
     * @param sql 指定的sql欲绝
     * @return SQL元数据信息
     * @throws SQLException 发生SQL错误时抛出该异常
     */
    SqlMetaData getMeta(DmDataSource cnn, String sql) throws SQLException;

    /**
     * 根据搜索关键字分页查询
     *
     * @param search   搜索关键字
     * @param pageable 分页参数
     * @return 查询结果
     */
    Page<Svc> list(String search, Set<String> label, Pageable pageable);

    /**
     * 列出标签
     *
     * @param pageable 分页参数
     * @return 查询到的标签的列表
     */
    Page<String> listLabels(Pageable pageable);
}
