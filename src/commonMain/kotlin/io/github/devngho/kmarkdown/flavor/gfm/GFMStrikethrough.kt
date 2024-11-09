package io.github.devngho.kmarkdown.flavor.gfm

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.*
import io.github.devngho.kmarkdown.flavor.common.Block

@MarkdownDSLMarker
data class GFMStrikethrough(var element: MarkdownElement): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = GFMStrikethrough

    override fun encode(): String = "~~${element.encode()}~~"

    companion object: MarkdownElementDescriptor<GFMStrikethrough> {
        fun MarkdownDSL.strikethrough(block: Block.BlockDSL.() -> Unit) = GFMStrikethrough(Block(Block.BlockDSL(this.flavor).apply(block).build()))
        fun Block.BlockDSL.strikethrough(block: Block.BlockDSL.() -> Unit) = GFMStrikethrough(Block(Block.BlockDSL(flavor).apply(block).build()))

        override val id: String = "strikethrough"
        override val flavor: Flavor = GFMFlavor

        override fun convertToFlavor(element: GFMStrikethrough, flavor: Flavor): MarkdownElement {
            return GFMStrikethrough(element)
        }

        override fun convertFromElement(element: MarkdownElement): GFMStrikethrough? = null
    }
}