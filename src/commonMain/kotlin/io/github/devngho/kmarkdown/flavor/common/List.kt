package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import kotlin.collections.List as KList

@MarkdownDSLMarker
data class List(val items: KList<MarkdownElement>, val listStyle: ListStyle): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = List

    @MarkdownDSLMarker
    class ListDSL internal constructor(private val listStyle: ListStyle, val flavor: Flavor) {
        private val items = mutableListOf<MarkdownElement>()

        fun item(block: Block.BlockDSL.() -> Unit) {
            items.add(Block(Block.BlockDSL(flavor).apply(block).build()))
        }

        fun list(listStyle: ListStyle, block: ListDSL.() -> Unit) {
            items.add(List(ListDSL(listStyle, flavor).apply(block).items, listStyle))
        }

        fun build(): List = List(items, listStyle)
    }

    enum class ListStyle {
        ORDERED,
        UNORDERED
    }

    override fun encode(): String = items.mapIndexed() { index, it ->
        when (it) {
            is List -> it.encode().prependIndent("    ")
            is Block -> {
                when (listStyle) {
                    ListStyle.ORDERED -> "${index + 1}. " + it.encode()
                    ListStyle.UNORDERED -> "- " + it.encode()
                }
            }
            else -> throw IllegalArgumentException("List items must be of type List or Block but was $it")
        }
    }.joinToString("\n")

    companion object: MarkdownElementDescriptor<List> {
        fun MarkdownDSL.list(listStyle: ListStyle, block: ListDSL.() -> Unit) = ListDSL(listStyle, this.flavor).apply(block).build()

        override val id: String = "list"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: List, flavor: Flavor): MarkdownElement {
            return List(element.items.map { it.convertTo(flavor) }, element.listStyle)
        }
        override fun convertFromElement(element: MarkdownElement): List? = null
    }
}