package io.github.devngho.kmarkdown.flavor.gfm

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.common.Text

object GFMStrikethrough: Text.TextStyle(), MarkdownElementDescriptor<GFMStrikethrough> {
    override fun apply(text: String): String {
        return "~~$text~~"
    }

    override val descriptor = this

    override val id: String = "text.strikethrough"
    override val flavor = GFMFlavor

    override fun convertToFlavor(element: GFMStrikethrough, flavor: Flavor): MarkdownElement? = null
    override fun convertFromElement(element: MarkdownElement): GFMStrikethrough? = null
}