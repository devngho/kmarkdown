package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.flavor.common.Text
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class TextTest: BehaviorSpec({
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