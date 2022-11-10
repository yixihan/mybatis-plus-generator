package com.yixihan.util;

import java.io.File;
import java.util.Objects;

/**
 * 输出目录清空工具
 *
 * @author yixihan
 * @date 2022/11/10 9:21
 */
public class FileUtils {

    public static void emptyDir(File dir) {
        if (!dir.isDirectory ()) {
            return;
        }
        File[] files = dir.listFiles ();
        assert files != null;
        for (File file : files) {
            deleteRecursive (file);
        }
    }

    public static void deleteRecursive(File file) {
        while (file.exists ()) {
            deleteLeafFile (file);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void deleteLeafFile(File file) {
        if (file.isFile ()) {
            file.delete ();
            return;
        }
        if (Objects.requireNonNull (file.list ()).length == 0) {
            file.delete ();
            return;
        }
        for (File file1 : Objects.requireNonNull (file.listFiles ())) {
            deleteLeafFile (file1);
        }
    }
}
