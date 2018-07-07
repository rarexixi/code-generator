package org.xi.quick.codegeneratorkt.utils

object SystemUtils {

    var SYSTEM_SLASH: String
    var REGEX_SYSTEM_SLASH: String
    var NEW_LINE: String

    init {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            SYSTEM_SLASH = """\"""
            REGEX_SYSTEM_SLASH = """\\"""
            NEW_LINE = "\r\n"
        } else {
            SYSTEM_SLASH = "/"
            REGEX_SYSTEM_SLASH = "/"
            NEW_LINE = "\n"
        }
    }
}
