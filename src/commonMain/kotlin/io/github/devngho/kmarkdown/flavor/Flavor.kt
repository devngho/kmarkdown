package io.github.devngho.kmarkdown.flavor

interface Flavor {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T: MarkdownElement> T.convertTo(flavor: Flavor): MarkdownElement {
            if (this.descriptor == flavor.elements[this.descriptor.id]) return this

            flavor.elements[this.descriptor.id]?.convertFromElement(this)?.let { return it }
            (this.descriptor as MarkdownElementDescriptor<T>).convertToFlavor(this, flavor)?.let { return it }

            throw IllegalArgumentException("Element cannot be converted. Tried to convert \"${this.descriptor.id}\" to $flavor")
        }
    }

    val elements: Map<String, MarkdownElementDescriptor<*>>
    val staticElements: Map<String, MarkdownElementDescriptor<*>>

    fun build(elements: List<MarkdownElement>): String
}