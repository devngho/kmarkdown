package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.flavor.common.List
import io.github.devngho.kmarkdown.flavor.common.Paragraph
import io.github.devngho.kmarkdown.flavor.common.Text
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec

class ListTest: StringSpec({
    "Trying to create list with paragraph should throw" {
        shouldThrow<IllegalArgumentException> { List(listOf(Paragraph(listOf(Text("Hello, World!")))), List.ListStyle.ORDERED).encode() }
    }
})