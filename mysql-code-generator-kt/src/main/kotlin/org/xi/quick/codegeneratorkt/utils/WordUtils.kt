package org.xi.quick.codegeneratorkt.utils

import java.util.regex.Pattern

object WordUtils {

    // 三、单数 -> 复数
    private val rules: List<Rule> = getSingularToPlural()

    private fun getSingularToPlural(): List<Rule> {
        val result: MutableList<Rule> = ArrayList(16)

        // 以辅音字母+y结尾的名词,将y改变为i,再加-es.
        result.add(Rule(Pattern.compile("([^aeiou])y$", Pattern.CASE_INSENSITIVE), "$1ies"))

        // 以-o结尾的名词,如果不是外来词或缩写,就加-es,否则加-s构成复数.
        result.add(Rule(Pattern.compile("([^aeiou])o$", Pattern.CASE_INSENSITIVE), "$1oes"))

        // 以-f或-fe结尾的名词,多为将-f或-fe改变为-ves
        result.add(Rule(Pattern.compile("(fe|f)$", Pattern.CASE_INSENSITIVE), "ves"))

        // 以-us结尾的名词（多为外来词）,通常将-us改变为-i构成复数.
        // 以-um结尾的名词,通常将-um改变为-i.
        result.add(Rule(Pattern.compile("u[ms]$", Pattern.CASE_INSENSITIVE), "i"))

        // 以-is结尾的名词,通常将-is改变为-es.
        result.add(Rule(Pattern.compile("(is)$", Pattern.CASE_INSENSITIVE), "es"))

        // 以-ix结尾的名词,通常将-ix改变为-ices.
        result.add(Rule(Pattern.compile("(ix)$", Pattern.CASE_INSENSITIVE), "ices"))

        // 以s、z、x、ch、sh结尾的词,在该词末尾加上后辍-es构成复数.
        result.add(Rule(Pattern.compile("(s|x|z|ch|sh)$", Pattern.CASE_INSENSITIVE), "$1es"))

        return result
    }

    // 单数 -> 复数
    fun pluralize(word: String?): String {
        if (word.isNullOrEmpty()) {
            return ""
        }
        for (regular in rules) {
            val matcher = regular.pattern.matcher(word)
            if (matcher.find()) {
                return matcher.replaceFirst(regular.replacement)
            }
        }
        return word + "s"
    }

    private class Rule(var pattern: Pattern, var replacement: String)
}