package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
data class InlineCodeBlock(val text: String) : MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = InlineCodeBlock

    override fun encode(): String = "`${text}`"

    companion object: MarkdownElementDescriptor<InlineCodeBlock> {
        fun MarkdownDSL.inlineCodeBlock(text: String) {
            add(InlineCodeBlock(text))
        }

        @Suppress("UnusedReceiverParameter")
        fun Block.BlockDSL.inlineCodeBlock(text: String) = InlineCodeBlock(text)

        override val id: String = "inline_codeblock"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: InlineCodeBlock, flavor: Flavor): MarkdownElement? = null
        override fun convertFromElement(element: MarkdownElement): InlineCodeBlock? = null
    }
}