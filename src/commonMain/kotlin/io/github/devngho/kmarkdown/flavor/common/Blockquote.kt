package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
data class Blockquote(val children: Paragraph) : MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Blockquote

    override fun encode(): String = children.encode().prependIndent("> ")

    companion object: MarkdownElementDescriptor<Blockquote> {
        fun MarkdownDSL.blockquote(block: MarkdownDSL.() -> Unit) = Blockquote(Paragraph(MarkdownDSL(this.flavor).apply(block).elements))

        override val id: String = "blockquote"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Blockquote, flavor: Flavor): MarkdownElement? {
            return (element.convertTo(flavor) as? Paragraph)?.children?.let { Blockquote(Paragraph(it)) }
        }

        override fun convertFromElement(element: MarkdownElement): Blockquote? = null
    }
}