package io.github.devngho.kmarkdown.flavor

interface MarkdownElementDescriptor<T: MarkdownElement> {
    val id: String
    val flavor: Flavor

    fun convertToFlavor(element: T, flavor: Flavor): MarkdownElement?
    fun convertFromElement(element: MarkdownElement): T?
}