package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

object Italic: Text.TextStyle(), MarkdownElementDescriptor<Italic> {
    override fun apply(text: String): String {
        return "*$text*"
    }

    override val descriptor = this

    override val id: String = "text.italic"
    override val flavor = CommonFlavor

    override fun convertToFlavor(element: Italic, flavor: Flavor): MarkdownElement? = null
    override fun convertFromElement(element: MarkdownElement): Italic? = null
}