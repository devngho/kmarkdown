package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.flavor.common.Bold
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec

class TextStyleTest: StringSpec({
    "Trying to encode TextStyle should fail" {
        shouldThrow<IllegalArgumentException> { Bold.encode() }
    }
})