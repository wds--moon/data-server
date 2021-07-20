package com.dm.ds.controller;

import com.dm.ds.collections.CollectionUtils;
import com.dm.ds.services.DataService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据集服务的相应请求<br />
 * MVC的标准分页请求，该请求以page,size作为分页参数，page最小是0,size是每页大小,参见{@link Pageable} <br />
 * size 的默认大小是10,最大值不能超过1000
 *
 * @author LiDong
 * @see Pageable
 */
@RestController
@RequestMapping(value = {"dcds/service", "service", "/s/dcds/service", "/ds/s/dcds/service", "services"})
@RequiredArgsConstructor
public class DataServiceController {

    private final DataService dataService;

    private final List<String> unusedKeys = Arrays.asList("draw", "token", "page", "rows", "size", "sort");


    /**
     * 查询结果集
     *
     * @param service  服务名称
     * @param pageable 分页参数
     * @param params   附加查询参数
     * @return 查询结果
     */
    @GetMapping(value = "{serviceName}")
    public Page<Map<String, Object>> query(
        @PathVariable("serviceName") String service,
        @PageableDefault() Pageable pageable,
        @RequestParam MultiValueMap<String, String> params) {
        return dataService.queryData(service, rebuildParams(params), pageable);
    }

    /**
     * 移除白痴参数
     *
     * @param params 待移除参数的参数清单
     * @return 移除之后的参数列表
     */
    private MultiValueMap<String, String> rebuildParams(MultiValueMap<String, String> params) {
        LinkedMultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        params.forEach((key, values) -> {
            if (!unusedKeys.contains(key)) {
                List<String> last = values.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(last)) {
                    result.put(key, last);
                }
            }
        });
        return result;
    }
}
