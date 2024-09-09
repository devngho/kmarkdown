package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.*
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.util.Escaper.escaped

@MarkdownDSLMarker
data class Text(var text: String, val styles: MutableList<TextStyle> = mutableListOf()): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Text

    abstract class TextStyle: MarkdownElement {
        abstract fun apply(text: String): String
        override fun encode(): String = throw IllegalArgumentException("TextStyle cannot be encoded")
    }

    override fun encode(): String {
        return styles.fold(text.escaped()) { acc, style -> style.apply(acc) }
    }

    companion object: MarkdownElementDescriptor<Text> {
        fun MarkdownDSL.text(text: String, vararg style: TextStyle, block: Text.() -> Unit = {}) {
            val element = Text(text, style.toMutableList())
            element.block()
            add(element)
        }

        override val id: String = "text"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Text, flavor: Flavor): MarkdownElement {
            return Text(element.text, element.styles.map { it.convertTo(flavor) as TextStyle }.toMutableList())
        }

        override fun convertFromElement(element: MarkdownElement): Text? = null
    }
}