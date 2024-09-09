package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import kotlin.collections.List

@MarkdownDSLMarker
data class Paragraph(val children: List<MarkdownElement>): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Paragraph

    override fun encode(): String = children.joinToString(separator = "\n\n") { it.encode() }

    companion object: MarkdownElementDescriptor<Paragraph> {
        fun MarkdownDSL.paragraph(block: MarkdownDSL.() -> Unit) {
            val element = Paragraph(MarkdownDSL(this.flavor).apply(block).elements)
            add(element)
        }

        override val id: String = "paragraph"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Paragraph, flavor: Flavor): MarkdownElement {
            return Paragraph(element.children.map { it.convertTo(flavor) })
        }
        override fun convertFromElement(element: MarkdownElement): Paragraph? = null
    }
}