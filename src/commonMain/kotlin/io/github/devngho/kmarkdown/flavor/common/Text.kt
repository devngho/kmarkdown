package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.util.Escaper.escaped

@MarkdownDSLMarker
data class Text(var text: String): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Text

    override fun encode(): String = text.escaped()

    companion object: MarkdownElementDescriptor<Text> {
        fun MarkdownDSL.text(text: String) = Text(text)
        fun Block.BlockDSL.text(text: String) = Text(text)

        override val id: String = "text"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Text, flavor: Flavor): MarkdownElement {
            return Text(element.text)
        }

        override fun convertFromElement(element: MarkdownElement): Text? = null
    }
}