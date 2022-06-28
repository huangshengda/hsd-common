package com.hsd.version;

/**
 * 类注释
 *
 * @author huangshengda
 * @date 2022-06-28 10:50
 */
public class VersionUtil {

    private VersionUtil() {
    }

    public static boolean validate(String version) {
        if (version != null && version.length() != 0) {
            String[] elements = version.split("\\.");
            for (String element : elements) {
                try {
                    int i = Integer.parseInt(element);
                    if (i < 0) {
                        return false;
                    }
                } catch (NumberFormatException var5) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static int compare(String versionA, String versionB) {
        boolean validateA = validate(versionA);
        boolean validateB = validate(versionB);
        if (validateA && validateB) {
            int[] versionASplit = strToArray(versionA);
            int[] versionBSplit = strToArray(versionB);
            int j;
            if (versionASplit.length != versionBSplit.length) {
                if (versionASplit.length > versionBSplit.length) {
                    for (j = 0; j < versionBSplit.length; ++j) {
                        if (versionASplit[j] > versionBSplit[j]) {
                            return 1;
                        }

                        if (versionASplit[j] < versionBSplit[j]) {
                            return -1;
                        }
                    }

                    for (j = versionASplit.length - 1; j >= versionBSplit.length; --j) {
                        if (versionASplit[j] != 0) {
                            return 1;
                        }
                    }

                    return 0;
                } else {
                    for (j = 0; j < versionASplit.length; ++j) {
                        if (versionASplit[j] > versionBSplit[j]) {
                            return 1;
                        }

                        if (versionASplit[j] < versionBSplit[j]) {
                            return -1;
                        }
                    }

                    for (j = versionBSplit.length - 1; j >= versionASplit.length; --j) {
                        if (versionBSplit[j] != 0) {
                            return -1;
                        }
                    }

                    return 0;
                }
            } else {
                for (j = 0; j < versionASplit.length; ++j) {
                    if (versionASplit[j] > versionBSplit[j]) {
                        return 1;
                    }

                    if (versionASplit[j] < versionBSplit[j]) {
                        return -1;
                    }
                }

                return 0;
            }
        } else if (!validateA && !validateB) {
            return 0;
        } else {
            return !validateA ? -1 : 1;
        }
    }

    private static int[] strToArray(String version) {
        String[] s = version.split("\\.");
        int[] versionNum = new int[s.length];

        for (int i = 0; i < s.length; ++i) {
            int num = Integer.parseInt(s[i]);
            versionNum[i] = num;
        }

        return versionNum;
    }
}
