package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.flavor.common.Text
import io.github.devngho.kmarkdown.flavor.common.Bold
import io.github.devngho.kmarkdown.flavor.common.Italic
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class TextTest: BehaviorSpec({
    given("A text") {
        val text = Text("Hello, World!")

        `when`("I apply bold style") {
            val boldText = text.copy(styles = mutableListOf(Bold))

            then("the text should be bold") {
                boldText.encode() shouldBe "**Hello, World\\!**"
            }
        }

        `when`("I apply bold style and italic style") {
            val styledText = text.copy(styles = mutableListOf(Bold, Italic))

            then("the text should be bold and italic") {
                styledText.encode() shouldBe "***Hello, World\\!***"
            }
        }
    }

    given("A text with special characters") {
        val text = Text("Hello, World! **Hello, World!** ***Hello, World!***")

        `when`("I encode the text") {
            val encodedText = text.encode()

            then("the text should be escaped") {
                encodedText shouldBe "Hello, World\\! \\*\\*Hello, World\\!\\*\\* \\*\\*\\*Hello, World\\!\\*\\*\\*"
            }
        }
    }
})