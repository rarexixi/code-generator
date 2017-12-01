package org.xi.quick.codegenerator.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xi on 5/17/2017.
 */
public class FileUtil {

    //region 删除

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    public static void delete(String filePath) {
        deleteIfExists(new File(filePath));
    }

    /**
     * 删除文件（如果存在）
     *
     * @param file 文件
     */
    public static void deleteIfExists(File file) {
        if (file.exists()) file.delete();
    }

    //endregion

    //region 读取

    /**
     * 读取文件所有行
     *
     * @param file 文件
     * @return 文件的所有行
     * @throws IOException
     */
    public static List<String> readAllLines(File file) throws IOException {

        return readAllLines(file.getAbsolutePath());
    }

    /**
     * 读取文件所有行
     *
     * @param filePath 文件路径
     * @return 文件的所有行
     * @throws IOException
     */
    public static List<String> readAllLines(String filePath) throws IOException {

        try (FileReader reader = new FileReader(filePath);
             BufferedReader br = new BufferedReader(reader);) {

            List<String> lines = new ArrayList<>();
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }

    //endregion

    //region 写入

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param content  内容
     * @throws IOException
     */
    public static void writeToFile(String filePath, String content) throws IOException {

        writeToFile(filePath, content, false);
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param content  内容
     * @param append   是否追加
     * @throws IOException
     */
    public static void writeToFile(String filePath, String content, boolean append) throws IOException {

        try (FileWriter writer = new FileWriter(filePath, append)) {
            writer.write(content);
        }
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param lines    行
     * @throws IOException
     */
    public static void writeToFile(String filePath, List<String> lines) throws IOException {

        writeToFile(filePath, lines, false);
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param lines    行
     * @param append   是否追加
     * @throws IOException
     */
    public static void writeToFile(String filePath, List<String> lines, boolean append) throws IOException {

        String newLine = "\n";
        try (FileWriter writer = new FileWriter(filePath, append)) {
            for (String line : lines) {
                writer.write(line + newLine);
            }
        }
    }

    //endregion
}
