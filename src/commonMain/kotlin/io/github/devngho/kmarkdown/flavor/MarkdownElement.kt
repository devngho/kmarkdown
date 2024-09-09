package io.github.devngho.kmarkdown.flavor

interface MarkdownElement {
    /** It is a descriptor of the markdown element. It should be MarkdownElementDescriptor<{THIS}> */
    val descriptor: MarkdownElementDescriptor<out MarkdownElement>

    fun encode(): String
}