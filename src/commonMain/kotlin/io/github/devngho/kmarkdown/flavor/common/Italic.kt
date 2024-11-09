package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
data class Italic(var element: MarkdownElement): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Text

    override fun encode(): String = "*${element.encode()}*"

    companion object: MarkdownElementDescriptor<Italic> {
        fun MarkdownDSL.italic(block: Block.BlockDSL.() -> Unit) = Italic(Block(Block.BlockDSL(this.flavor).apply(block).build()))
        fun Block.BlockDSL.italic(block: Block.BlockDSL.() -> Unit) = Italic(Block(Block.BlockDSL(flavor).apply(block).build()))

        override val id: String = "italic"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Italic, flavor: Flavor): MarkdownElement {
            return Italic(element.element)
        }

        override fun convertFromElement(element: MarkdownElement): Italic? = null
    }
}