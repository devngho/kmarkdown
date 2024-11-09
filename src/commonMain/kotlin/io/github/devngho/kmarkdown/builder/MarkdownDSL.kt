package io.github.devngho.kmarkdown.builder

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.common.CommonFlavor

@MarkdownDSLMarker
class MarkdownDSL internal constructor(val flavor: Flavor) {
    val elements = mutableListOf<MarkdownElement>()

    fun add(element: MarkdownElement) {
        elements.add(element)
    }

    operator fun MarkdownElement.unaryPlus() {
        elements.add(this)
    }

    operator fun List<MarkdownElement>.unaryPlus() {
        elements.addAll(this)
    }

    fun build(): String = flavor.build(elements.map { it.convertTo(flavor) })

    companion object {
        fun markdown(flavor: Flavor = CommonFlavor, block: MarkdownDSL.() -> Unit): String {
            val dsl = MarkdownDSL(flavor)
            dsl.block()
            return dsl.build()
        }
    }
}