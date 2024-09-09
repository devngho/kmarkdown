package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

object Bold: Text.TextStyle(), MarkdownElementDescriptor<Bold> {
    override fun apply(text: String): String {
        return "**$text**"
    }

    override val descriptor = this

    override val id: String = "text.bold"
    override val flavor = CommonFlavor

    override fun convertToFlavor(element: Bold, flavor: Flavor): MarkdownElement? = null
    override fun convertFromElement(element: MarkdownElement): Bold? = null
}