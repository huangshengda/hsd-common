package com.hsd.rpc.invoke.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 使Function获取序列化能力
 *
 * @author huangshengda
 * @date 2022-06-25 14:52
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {

}
