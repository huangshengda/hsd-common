package com.hsd.jvm.compile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2020/9/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JavaParseResult {

    private String packageName;

    private String simpleClassName;

    public String getClassName() {
        return String.format("%s.%s", packageName, simpleClassName);
    }

}
