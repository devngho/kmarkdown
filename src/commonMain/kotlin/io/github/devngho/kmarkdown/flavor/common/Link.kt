package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

data class Link(val text: Block, val url: String): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Link

    override fun encode(): String = "[${text.encode()}]($url)"

    companion object: MarkdownElementDescriptor<Link> {
        fun MarkdownDSL.link(text: Block, url: String) {
            add(Link(text, url))
        }

        fun MarkdownDSL.link(text: String, url: String) = link(Block(listOf(Text(text))), url)
        fun MarkdownDSL.link(url: String, block: Block.BlockDSL.() -> Unit) = link(Block(Block.BlockDSL(flavor).apply(block).build()), url)

        @Suppress("UnusedReceiverParameter")
        fun Block.BlockDSL.link(text: Block, url: String) = Link(text, url)
        fun Block.BlockDSL.link(text: String, url: String) = link(Block(listOf(Text(text))), url)
        fun Block.BlockDSL.link(url: String, block: Block.BlockDSL.() -> Unit) = link(Block(Block.BlockDSL(flavor).apply(block).build()), url)

        override val id: String = "link"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Link, flavor: Flavor): MarkdownElement? {
            return Link(element.text.convertTo(flavor) as? Block ?: return null, element.url)
        }
        override fun convertFromElement(element: MarkdownElement): Link? = null
    }
}