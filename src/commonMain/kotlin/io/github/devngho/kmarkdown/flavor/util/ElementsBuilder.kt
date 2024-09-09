package io.github.devngho.kmarkdown.flavor.util

import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor

internal fun buildElements(vararg elements: MarkdownElementDescriptor<*>): Map<String, MarkdownElementDescriptor<*>> =
    elements.associateBy { it.id }