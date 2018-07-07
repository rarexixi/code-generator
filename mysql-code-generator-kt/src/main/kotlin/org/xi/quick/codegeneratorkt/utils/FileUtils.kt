package org.xi.quick.codegeneratorkt.utils

import java.io.*
import java.util.ArrayList

object FileUtils {

    //region 删除

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     */
    fun delete(filePath: String) {
        deleteIfExists(File(filePath))
    }

    /**
     * 删除文件（如果存在）
     *
     * @param file 文件
     */
    fun deleteIfExists(file: File) {
        if (file.exists()) file.delete()
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
    @Throws(IOException::class)
    fun readAllLines(file: File): List<String> {
        return readAllLines(file.absolutePath)
    }

    /**
     * 读取文件所有行
     *
     * @param filePath 文件路径
     * @return 文件的所有行
     * @throws IOException
     */
    @Throws(IOException::class)
    fun readAllLines(filePath: String): List<String> {

        FileReader(filePath).use { reader ->
            BufferedReader(reader).use { br ->

                val lines = ArrayList<String>()
                var line: String? = br.readLine()
                while (line != null) {
                    lines.add(line)
                    line = br.readLine()
                }
                return lines
            }
        }
    }

    //endregion

    //region 写入

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param content  内容
     * @param append   是否追加
     * @throws IOException
     */
    @Throws(IOException::class)
    @JvmOverloads
    fun writeToFile(filePath: String, content: String, append: Boolean = false) {

        FileWriter(filePath, append).use { writer -> writer.write(content) }
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param lines    行
     * @param append   是否追加
     * @throws IOException
     */
    @Throws(IOException::class)
    @JvmOverloads
    fun writeToFile(filePath: String, lines: List<String>, append: Boolean = false) {

        FileWriter(filePath, append).use { writer ->
            lines.forEach {
                writer.write(it + SystemUtils.NEW_LINE)
            }
        }
    }

    //endregion
}