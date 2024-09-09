package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.common.Block
import io.github.devngho.kmarkdown.flavor.common.CommonFlavor
import io.github.devngho.kmarkdown.flavor.common.Heading
import io.github.devngho.kmarkdown.flavor.gfm.GFMFlavor
import io.github.devngho.kmarkdown.flavor.common.Text
import io.github.devngho.kmarkdown.flavor.util.buildElements
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf

class FlavorTest: BehaviorSpec({
    given("GitHub flavored markdown") {
        and("A unknown text style") {
            `when`("I convert to common flavor text") {
                val convertedTextStyle = runCatching { TestTextStyle.convertTo(GFMFlavor) }

                then("I should get an IllegalArgumentException") {
                    convertedTextStyle.isFailure shouldBe true
                    convertedTextStyle.exceptionOrNull().shouldBeInstanceOf<IllegalArgumentException>()
                    convertedTextStyle.exceptionOrNull()?.message shouldStartWith  "Element cannot be converted. Tried to convert \"unknown\" to io.github.devngho.kmarkdown.flavor.gfm.GFMFlavor"
                }
            }
        }

        and("A common heading") {
            val heading = Heading(1, Block(listOf(Text("Hello, World!"))))

            `when`("I convert to test flavor") {
                val convertedHeading = heading.convertTo(TestFlavor)

                then("I should get a test heading") {
                    convertedHeading.shouldBeInstanceOf<TestHeading>()
                    convertedHeading.encode() shouldBe "# Hello, World\\! #"
                }
            }
        }
    }
}) {
    object TestFlavor: Flavor {
        override val elements: Map<String, MarkdownElementDescriptor<*>> = buildElements(TestHeading)
        override val staticElements: Map<String, MarkdownElementDescriptor<*>> = buildElements(TestTextStyle)

        override fun build(elements: List<MarkdownElement>): String = CommonFlavor.build(elements)
    }

    object TestTextStyle: Text.TextStyle(), MarkdownElementDescriptor<TestTextStyle> {
        override val descriptor = this

        override fun apply(text: String): String {
            return text
        }

        override val id: String = "unknown"
        override val flavor: Flavor = TestFlavor

        override fun convertToFlavor(element: TestTextStyle, flavor: Flavor): MarkdownElement? = null
        override fun convertFromElement(element: MarkdownElement): TestTextStyle? = null
    }

    data class TestHeading(val level: Int, val block: Block): MarkdownElement {
        override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = TestHeading

        override fun encode(): String = "#".repeat(level) + " " + block.encode() + " " + "#".repeat(level)

        companion object: MarkdownElementDescriptor<TestHeading> {
            override val id: String = "heading"
            override val flavor: Flavor = TestFlavor

            override fun convertToFlavor(element: TestHeading, flavor: Flavor): MarkdownElement? = null

            override fun convertFromElement(element: MarkdownElement): TestHeading? = when(element) {
                is Heading -> TestHeading(element.level, element.block)
                else -> null
            }
        }
    }
}