package com.hsd.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TryUtil {
    public static <T> void safeInvoke(Runnable r) {
        safeInvoke(r, "");
    }

    public static <T> void safeInvoke(Runnable r, String errorMsg) {
        try {
            r.run();
        } catch (Exception e) {
            log.error(errorMsg, e);
        }
    }

}
