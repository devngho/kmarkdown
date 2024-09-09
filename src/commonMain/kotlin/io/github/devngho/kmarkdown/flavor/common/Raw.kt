package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

data class Raw(val body: String): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Raw

    override fun encode(): String = body

    companion object: MarkdownElementDescriptor<Raw> {
        fun MarkdownDSL.raw(body: String) {
            add(Raw(body))
        }

        @Suppress("UnusedReceiverParameter")
        fun Block.BlockDSL.raw(body: String) = Raw(body)

        override val id: String = "raw"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Raw, flavor: Flavor): MarkdownElement? = null
        override fun convertFromElement(element: MarkdownElement): Raw? = null
    }
}