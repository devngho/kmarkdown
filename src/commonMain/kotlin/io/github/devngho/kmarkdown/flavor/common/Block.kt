package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
data class Block(val children: kotlin.collections.List<MarkdownElement>): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Block

    @MarkdownDSLMarker
    class BlockDSL(val flavor: Flavor) {
        private val elements = mutableListOf<MarkdownElement>()

        operator fun String.unaryPlus() {
            elements.add(Text(this))
        }

        operator fun MarkdownElement.unaryPlus() {
            elements.add(this)
        }

        operator fun kotlin.collections.List<MarkdownElement>.unaryPlus() {
            this.forEachIndexed { index, markdownElement ->
                if (index != 0) elements.add(Text(" "))
                elements.add(markdownElement)
            }
        }

        /** Combines two elements without a space between them. */
        operator fun MarkdownElement.plus(element: MarkdownElement): MarkdownElement = Block(listOf(this, element))
        /** Combines two elements without a space between them. */
        operator fun MarkdownElement.plus(text: String): MarkdownElement = Block(listOf(this, Text(text)))

        @Suppress("FunctionName")
        /** Combines two elements with a space between them. */
        infix fun MarkdownElement.`_`(element: MarkdownElement) = Block(listOf(this, Text(" "), element))
        @Suppress("FunctionName")
        /** Combines two elements with a space between them. */
        infix fun MarkdownElement.`_`(text: String) = Block(listOf(this, Text(" "), Text(text)))
        @Suppress("FunctionName")
        /** Combines two elements with a space between them. */
        infix fun String.`_`(element: MarkdownElement) = Block(listOf(Text(this), Text(" "), element))

        fun build(): kotlin.collections.List<MarkdownElement> = elements.map { it.convertTo(flavor) }
    }

    override fun encode(): String = children.joinToString("") { it.encode() }

    companion object: MarkdownElementDescriptor<Block> {
        fun MarkdownDSL.block(block: BlockDSL.() -> Unit) = Block(BlockDSL(this.flavor).apply(block).build())

        override val id: String = "block"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Block, flavor: Flavor): MarkdownElement {
            return Block(element.children.map { it.convertTo(flavor) })
        }

        override fun convertFromElement(element: MarkdownElement): Block? = null
    }
}