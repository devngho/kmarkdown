package io.github.devngho.kmarkdown.flavor.util

object Escaper {
    private val escapeTargets = listOf("\\", "`", "*", "_", "{", "}", "[", "]", "<", ">", "(", ")", "#", "+", "-", ".", "!", "|")

    fun escape(text: String): String = escapeTargets.fold(text) { acc, target -> acc.replace(target, "\\$target") }
    fun String.escaped(): String = escape(this)
}