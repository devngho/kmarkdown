package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.common.Paragraph.Companion.paragraph

@MarkdownDSLMarker
data class Heading(val level: Int, val block: Block): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Heading

    override fun encode(): String {
        return "${"#".repeat(level)} ${block.encode()}"
    }

    companion object: MarkdownElementDescriptor<Heading> {
        fun MarkdownDSL.heading(level: Int, block: Block.BlockDSL.() -> Unit = {}, block2: MarkdownDSL.() -> Unit = {}) {
            val element = Heading(level, Block(Block.BlockDSL(CommonFlavor).apply(block).build()))
            add(element)
            paragraph { block2() }
        }

        fun MarkdownDSL.heading(level: Int, text: String, block2: MarkdownDSL.() -> Unit = {}) = heading(level, { +text }, block2)

        override val id: String = "heading"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Heading, flavor: Flavor): MarkdownElement? {
            return Heading(element.level, element.block.convertTo(flavor) as? Block ?: return null)
        }

        override fun convertFromElement(element: MarkdownElement): Heading? = null
    }
}