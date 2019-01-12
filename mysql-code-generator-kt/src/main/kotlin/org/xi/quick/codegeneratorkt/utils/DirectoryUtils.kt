package org.xi.quick.codegeneratorkt.utils

import java.io.File
import java.util.ArrayList

object DirectoryUtils {

    /**
     * 创建文件加如果不存在
     *
     * @param directoryPath 文件夹路径
     */
    fun createIfNotExists(directoryPath: String) {

        val folder = File(directoryPath)
        if (!folder.exists())
            folder.mkdirs()
    }

    /**
     * 获取文件夹以及子文件夹下所有文件
     *
     * @param directoryPath 文件夹路径
     * @return
     */
    fun getAllFiles(directoryPath: String): List<File> {

        val folder = File(directoryPath)

        return getAllFiles(folder)
    }

    /**
     * 获取文件夹以及子文件夹下所有文件
     *
     * @param folder 文件夹
     * @return
     */
    private fun getAllFiles(folder: File): List<File> {
        val files = ArrayList<File>()

        if (folder.exists()) {
            folder.listFiles()?.forEach { file ->
                apply {
                    if (file.isFile) {
                        files.add(file)
                    } else {
                        files.addAll(getAllFiles(file))
                    }
                }
            }
        }

        return files
    }
}