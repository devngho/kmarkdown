package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

@MarkdownDSLMarker
class HorizontalRule : MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = HorizontalRule

    override fun encode(): String = "---"

    companion object: MarkdownElementDescriptor<HorizontalRule> {
        fun MarkdownDSL.horizontalRule() = HorizontalRule()

        override val id: String = "horizontal_rule"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: HorizontalRule, flavor: Flavor): MarkdownElement? = null
        override fun convertFromElement(element: MarkdownElement): HorizontalRule? = null
    }
}