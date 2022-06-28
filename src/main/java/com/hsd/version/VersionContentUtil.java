package com.hsd.version;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-27 20:00
 */
public class VersionContentUtil {

    public static String getVal(List<VersionContent> versions, String version) {
        if (versions == null) {
            return null;
        }
        List<VersionContent> collect = versions.stream().filter(Objects::nonNull).sorted((o1, o2) -> VersionUtil.compare(o1.getStart(), o2.getStart())).collect(Collectors.toList());
        for (VersionContent versionContent : collect) {
            if (isEmpty(versionContent.getStart()) && isEmpty(versionContent.getEnd())) {
                return versionContent.getVal();
            } else if (isEmpty(versionContent.getStart())) {
                if (VersionUtil.compare(versionContent.getEnd(), version) >= 0) {
                    return versionContent.getVal();
                }
            } else if (isEmpty(versionContent.getEnd())) {
                if (VersionUtil.compare(versionContent.getStart(), version) <= 0) {
                    return versionContent.getVal();
                }
            } else {
                if (VersionUtil.compare(versionContent.getStart(), version) <= 0
                        && VersionUtil.compare(versionContent.getEnd(), version) >= 0) {
                    return versionContent.getVal();
                }
            }

        }
        return null;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

}
