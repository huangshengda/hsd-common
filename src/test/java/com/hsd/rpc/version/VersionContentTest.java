package com.hsd.rpc.version;

import com.hsd.version.VersionContent;
import com.hsd.version.VersionContentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-28 11:55
 */
public class VersionContentTest {

    public static void main(String[] args) {
        List<VersionContent> versions = new ArrayList<VersionContent>() {{
            add(new VersionContent("", "1.0", "11"));
            add(new VersionContent("4.0.0", "", "33"));
            add(new VersionContent("2.0.0", "3.0.0", "22"));
            add(null);
        }};
        String val = VersionContentUtil.getVal(versions, "1.0.1");
        System.out.println(val);

    }
}
