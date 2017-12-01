package org.xi.quick.codegenerator.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xi on 5/17/2017.
 */
public class DirectoryUtil {

    /**
     * 判断文件夹是否存在
     *
     * @param directoryPath 文件夹路径
     * @return
     */
    public static boolean exists(String directoryPath) {

        return new File(directoryPath).exists();
    }

    /**
     * 创建文件加如果不存在
     *
     * @param directoryPath 文件夹路径
     */
    public static void createIfNotExists(String directoryPath) {

        if (!exists(directoryPath)) {
            File Folder = new File(directoryPath);
            Folder.mkdirs();
        }
    }

    /**
     * 获取文件夹下所有文件和文件夹
     *
     * @param directoryPath 文件夹路径
     * @return
     */
    public static File[] getAllFilesAndFolder(String directoryPath) {

        File folder = new File(directoryPath);
        return folder.exists() ? folder.listFiles() : null;
    }

    /**
     * 获取文件夹以及子文件夹下所有文件
     *
     * @param directoryPath 文件夹路径
     * @return
     */
    public static List<File> getAllFiles(String directoryPath) {

        File folder = new File(directoryPath);

        return getAllFiles(folder);
    }

    /**
     * 获取文件夹以及子文件夹下所有文件
     *
     * @param folder 文件夹
     * @return
     */
    public static List<File> getAllFiles(File folder) {
        List<File> files = new ArrayList<>();

        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    files.add(file);
                } else {
                    files.addAll(getAllFiles(file));
                }
            }
        }

        return files;
    }

    /**
     * 删除文件夹下所有文件和文件夹
     *
     * @param directoryPath 文件夹路径
     */
    public static void delete(String directoryPath) {

        delete(new File(directoryPath));
    }


    /**
     * 删除文件夹下所有文件和文件夹
     *
     * @param directory 文件夹
     */
    public static void delete(File directory) {

        if (!directory.exists()) return;

        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0, len = files.length; i < len; i++) {
                if (files[i].isDirectory()) {
                    delete(files[i]);
                }
                files[i].delete();
            }
        }
    }
}
