package com.hsd.trace;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-29 15:41
 */
@Data
public class MethodTraceBody {

    private List<MethodBody> methodBodyList;

    private Integer count;

    private Integer level;

    public MethodTraceBody() {
        this.count = 0;
        this.level = 0;
        this.setMethodBodyList(new ArrayList<>());
    }

}
