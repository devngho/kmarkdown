package io.github.devngho.kmarkdown.flavor.gfm

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.common.CommonFlavor
import io.github.devngho.kmarkdown.flavor.util.buildElements

object GFMFlavor: Flavor by CommonFlavor {
    override val elements: Map<String, MarkdownElementDescriptor<*>> = CommonFlavor.elements + buildElements(
        GFMTaskList
    )

    override val staticElements: Map<String, MarkdownElementDescriptor<*>> = CommonFlavor.staticElements + buildElements(GFMStrikethrough)
}