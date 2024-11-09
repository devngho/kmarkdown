package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.util.buildElements

object CommonFlavor: Flavor {
    override val elements: Map<String, MarkdownElementDescriptor<*>> = buildElements(
        Raw,
        Text,
        Italic,
        Bold,
        Paragraph,
        Blockquote,
        Heading,
        CodeBlock,
        Block,
        InlineCodeBlock,
        Link,
        List,
        HorizontalRule,
        Table,
        Table.TableRow,
        Table.TableCol,
        Table.TableColOrdered
    )

    override fun build(elements: kotlin.collections.List<MarkdownElement>): String = elements.joinToString(separator = "\n\n") { it.encode() }
}