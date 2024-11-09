package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
data class Bold(var element: Block): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Text

    override fun encode(): String = "**${element.encode()}**"

    companion object: MarkdownElementDescriptor<Bold> {
        fun MarkdownDSL.bold(block: Block.BlockDSL.() -> Unit) = Bold(Block(Block.BlockDSL(this.flavor).apply(block).build()))
        fun Block.BlockDSL.bold(block: Block.BlockDSL.() -> Unit) = Bold(Block(Block.BlockDSL(flavor).apply(block).build()))

        override val id: String = "bold"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Bold, flavor: Flavor): MarkdownElement {
            return Bold(element.element)
        }

        override fun convertFromElement(element: MarkdownElement): Bold? = null
    }
}