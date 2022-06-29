package com.hsd.trace;

import lombok.Builder;
import lombok.Data;

/**
 * 方法体
 *
 * @author huangshengda
 * @date 2022-06-29 14:47
 */
@Data
@Builder
public class MethodBody {

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;


}
