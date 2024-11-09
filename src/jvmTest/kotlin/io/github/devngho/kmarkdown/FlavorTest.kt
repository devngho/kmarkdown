package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import io.github.devngho.kmarkdown.flavor.common.Block
import io.github.devngho.kmarkdown.flavor.common.CommonFlavor
import io.github.devngho.kmarkdown.flavor.common.Heading
import io.github.devngho.kmarkdown.flavor.common.Text
import io.github.devngho.kmarkdown.flavor.util.buildElements
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class FlavorTest: BehaviorSpec({
    given("GitHub flavored markdown") {
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

        override fun build(elements: List<MarkdownElement>): String = CommonFlavor.build(elements)
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