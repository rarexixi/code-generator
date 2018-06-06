package org.xi.quick.codegeneratorkt.utils

import java.io.File
import java.util.ArrayList

object DirectoryUtils {

    /**
     * 判断文件夹是否存在
     *
     * @param directoryPath 文件夹路径
     * @return
     */
    fun exists(directoryPath: String): Boolean = File(directoryPath).exists()

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
     * 获取文件夹下所有文件和文件夹
     *
     * @param directoryPath 文件夹路径
     * @return
     */
    fun getAllFilesAndFolder(directoryPath: String): Array<File>? {

        val folder = File(directoryPath)
        return if (folder.exists()) folder.listFiles() else null
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
    fun getAllFiles(folder: File): List<File> {
        val files = ArrayList<File>()

        if (folder.exists()) {
            for (file in folder.listFiles()!!) {
                if (file.isFile) {
                    files.add(file)
                } else {
                    files.addAll(getAllFiles(file))
                }
            }
        }

        return files
    }

    /**
     * 删除文件夹下所有文件和文件夹
     *
     * @param directoryPath 文件夹路径
     */
    fun delete(directoryPath: String) = delete(File(directoryPath))


    /**
     * 删除文件夹下所有文件和文件夹
     *
     * @param directory 文件夹
     */
    fun delete(directory: File) {

        if (!directory.exists()) return
        directory.listFiles()?.apply {
            this.forEach {
                if (it.isDirectory) {
                    delete(it)
                }
                it.delete()
            }
        }
    }
}