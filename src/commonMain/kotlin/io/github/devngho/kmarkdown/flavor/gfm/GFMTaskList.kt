package io.github.devngho.kmarkdown.flavor.gfm

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.common.Block
import kotlin.collections.List as KList

@MarkdownDSLMarker
data class GFMTaskList(val items: KList<Pair<Boolean, MarkdownElement>>): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = GFMTaskList

    @MarkdownDSLMarker
    class TaskListDSL internal constructor(val flavor: Flavor) {
        private val items = mutableListOf<Pair<Boolean, MarkdownElement>>()

        fun item(done: Boolean, block: Block.BlockDSL.() -> Unit) {
            items.add(done to Block(Block.BlockDSL(flavor).apply(block).build()))
        }

        fun gfmTaskList(block: TaskListDSL.() -> Unit) {
            items.add(true to GFMTaskList(TaskListDSL(flavor).apply(block).items))
        }

        fun build(): GFMTaskList = GFMTaskList(items)
    }

    override fun encode(): String = items.map {
        when (it.second) {
            is GFMTaskList -> it.second.encode().prependIndent("  ")
            is Block -> {
                "- ${if (it.first) "[x]" else "[ ]"} " + it.second.encode()
            }
            else -> throw IllegalArgumentException("List items must be of type List or Block but was $it")
        }
    }.joinToString("\n")

    companion object: MarkdownElementDescriptor<GFMTaskList> {
        fun MarkdownDSL.gfmTaskList(block: TaskListDSL.() -> Unit) {
            if (flavor !is GFMFlavor) throw IllegalArgumentException("GFMTaskList is only available in GFM flavor")

            val element = TaskListDSL(this.flavor).apply(block).build()
            add(element)
        }

        override val id: String = "task_list"
        override val flavor: Flavor = GFMFlavor

        override fun convertToFlavor(element: GFMTaskList, flavor: Flavor): MarkdownElement {
            return GFMTaskList(element.items.map { it.first to it.second.convertTo(flavor) })
        }

        override fun convertFromElement(element: MarkdownElement): GFMTaskList? = null
    }
}