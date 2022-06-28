package com.hsd.version;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-27 20:00
 */
@Data
@AllArgsConstructor
public class VersionContent {

    private String start;
    private String end;
    private String val;
}
