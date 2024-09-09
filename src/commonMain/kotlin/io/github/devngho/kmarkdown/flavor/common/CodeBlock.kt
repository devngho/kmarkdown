package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
data class CodeBlock(val text: String, val language: String? = null) : MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = CodeBlock

    override fun encode(): String = "```${language ?: ""}\n$text\n```"

    companion object: MarkdownElementDescriptor<CodeBlock> {
        fun MarkdownDSL.codeblock(text: String, language: String? = null) {
            add(CodeBlock(text, language))
        }

        override val id: String = "codeblock"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: CodeBlock, flavor: Flavor): MarkdownElement? = null
        override fun convertFromElement(element: MarkdownElement): CodeBlock? = null
    }
}