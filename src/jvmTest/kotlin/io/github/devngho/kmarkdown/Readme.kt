package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.builder.MarkdownDSL.Companion.markdown
import io.github.devngho.kmarkdown.flavor.common.Block.Companion.block
import io.github.devngho.kmarkdown.flavor.common.Blockquote.Companion.blockquote
import io.github.devngho.kmarkdown.flavor.common.Bold
import io.github.devngho.kmarkdown.flavor.common.CodeBlock.Companion.codeblock
import io.github.devngho.kmarkdown.flavor.common.Heading.Companion.heading
import io.github.devngho.kmarkdown.flavor.common.InlineCodeBlock.Companion.inlineCodeBlock
import io.github.devngho.kmarkdown.flavor.common.Link.Companion.link
import io.github.devngho.kmarkdown.flavor.common.List
import io.github.devngho.kmarkdown.flavor.common.List.Companion.list
import io.github.devngho.kmarkdown.flavor.common.Paragraph.Companion.paragraph
import io.github.devngho.kmarkdown.flavor.common.Raw.Companion.raw
import io.github.devngho.kmarkdown.flavor.gfm.GFMFlavor
import io.kotest.core.spec.style.StringSpec

class Readme: StringSpec({
    "README.md" {
        val document = markdown(GFMFlavor) {
            block {
                +("English"() + " | "() + link("한국어", "README_ko.md"))
            }

            heading(2, "kmarkdown") {
                block {
                    +("kmarkdown is a" _ "well-tested"(Bold) _ "and" _ "pure-Kotlin"(Bold) _ "Markdown library.")
                }
                block {
                    +("This README.MD was generated using kmarkdown." _ link("Check it out!", "src/jvmTest/kotlin/io/github/devngho/kmarkdown/Readme.kt"))
                }
            }

            heading(3, "Installation") {
                codeblock("""
                |implementation("io.github.devngho:kmarkdown:[VERSION]")
                """.trimMargin(), "kts")
            }

            heading(3, "Usage") {
                block {
                    +("To use kmarkdown, you can create a markdown document using the" _ inlineCodeBlock("markdown") _ "function.")
                }
                block {
                    +("The" _ inlineCodeBlock("markdown") _ "function takes a" _ "Flavor" _ "as a parameter (optional) and returns a markdown document.")
                }

                codeblock(
                    """
                |val document = markdown(GFMFlavor) {
                |    // Your markdown document here
                |}
                """.trimMargin(), "kotlin")

                heading(4, "Paragraph") {
                    block {
                        +("To create a paragraph, you can use the" _ inlineCodeBlock("paragraph") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("paragraph") _ "function takes a" _ "block" _ "as a parameter.")
                    }
                    block {
                        +("A paragraph represents multiple markdown elements, such as code blocks or list, separated by two newline.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |paragraph {
                    |    block {
                    |        +("This is a paragraph.")
                    |    }
                    |    block {
                    |        +("It can contain multiple blocks.")
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    paragraph {
                        block {
                            +("This is a paragraph.")
                        }
                        block {
                            +("It can contain multiple blocks.")
                        }
                    }
                }

                heading(4, "Block") {
                    block {
                        +("A block is a collection of markdown elements.")
                    }
                    block {
                        +("You can create a block using the" _ inlineCodeBlock("block") _ "function.")
                    }
                    block {
                        +("A block can contain multiple inline markdown elements like text, link.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |block {
                    |    +("This is a block. ")
                    |    +("It can contain multiple markdown elements.")
                    |    
                    |    // or you can use '_' infix function
                    |    // to concatenate markdown elements with a space
                    |    
                    |    +("This is another way to create a block."() _ "using the '_' infix function."())
                    |    
                    |    // + operator can concatenate markdown elements without space
                    |    
                    |    +("+ operator can concatenate "() + "markdown elements without space.")
                    |    
                    |    // or you can use list of markdown elements
                    |    
                    |    +listOf("You can also use a list of markdown elements."(), "to create a block.")
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    block {
                        +("This is a block. ")
                        +("It can contain multiple markdown elements.")

                        +("This is another way to create a block."() _ "using the '_' infix function."())

                        +("+ operator can concatenate "() + "markdown elements without space.")

                        +listOf("You can also use a list of markdown elements."(), "to create a block."())
                    }

                    blockquote {
                        block {
                            +"Why did you append () to the text?"(Bold)
                        }
                        block {
                            +("Because the text is a markdown element, and the function expects a markdown element. You can add styles to the text by appending styles to the text.")
                        }
                    }
                }

                heading(4, "Heading") {
                    block {
                        +("To create a heading, you can use the" _ inlineCodeBlock("heading") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("heading") _ "function takes a level and text as parameters.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |heading(2, "Hello, World!") {
                    |    block {
                    |        +("This is a heading.")
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    heading(2, "Hello, World!") {
                        block {
                            +("This is a heading.")
                        }
                    }
                }

                heading(4, "Link") {
                    block {
                        +("To create a link, you can use the" _ inlineCodeBlock("link") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("link") _ "function takes a text and url as parameters.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |link("This is a link", "https://example.com")
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    link("This is a link", "https://example.com")
                }

                heading(4, "List") {
                    block {
                        +("To create a list, you can use the" _ inlineCodeBlock("list") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("list") _ "function takes a list style as a parameter.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |list(List.ListStyle.ORDERED) {
                    |    item {
                    |        +"This is a list item."
                    |    }
                    |    list(List.ListStyle.UNORDERED) {
                    |        item {
                    |            +"This is a nested list item."
                    |        }
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    list(List.ListStyle.ORDERED) {
                        item {
                            +"This is a list item."
                        }
                        list(List.ListStyle.UNORDERED) {
                            item {
                                +"This is a nested list item."
                            }
                        }
                    }
                }

                heading(4, "Blockquote") {
                    block {
                        +("To create a blockquote, you can use the" _ inlineCodeBlock("blockquote") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("blockquote") _ "function takes a block as a parameter.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |blockquote {
                    |    block {
                    |        +("This is a blockquote.")
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    blockquote {
                        block {
                            +"This is a blockquote."
                        }
                    }
                }

                heading(4, "CodeBlock and InlineCodeBlock") {
                    block {
                        +("To create a code block, you can use the" _ inlineCodeBlock("codeblock") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("codeblock") _ "function takes a code block and language (optional) as parameters.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |codeblock("println(\"Hello, World!\")", "kotlin")
                    |codeblock("Language is optional.")
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    codeblock("println(\"Hello, World!\")", "kotlin")
                    codeblock("Language is optional.")

                    block {
                        +("To create an inline code block, you can use the" _ inlineCodeBlock("inlineCodeBlock") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("inlineCodeBlock") _ "function takes a code block as a parameter.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |inlineCodeBlock("println(\"Hello, World!\")")
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    inlineCodeBlock("println(\"Hello, World!\")")
                }

                heading(4, "Raw") {
                    blockquote {
                        block {
                            +("It's not recommended to use raw elements."(Bold))
                        }
                        block {
                            +("I recommend creating your custom flavor instead.")
                        }
                    }

                    block {
                        +("To create a raw element, you can use the" _ inlineCodeBlock("raw") _ "function.")
                    }
                    block {
                        +("The" _ inlineCodeBlock("raw") _ "function takes a text as a parameter.")
                    }

                    codeblock(
                        """
                    |// in markdown document
                    |raw("This is a raw element.")
                    """.trimMargin(), "kotlin")

                    block { +("Output:") }

                    raw("This is a raw element.")
                }
            }

            heading(3, "Tips") {
                heading(4, "Escape Markdown") {
                    block {
                        +("Your text will be escaped by default except you use" _ inlineCodeBlock("raw") _ " or code blocks.")
                    }

                    block {
                        +("The escape characters are:" _ raw("""```\, `, *, _, {, }, [, ], (, ), #, +, -, ., !```"""))
                    }
                }
            }

            heading(3, "License") {
                block {
                    +(link("MIT License", "https://github.com/devngho/kmarkdown/blob/main/LICENSE") _ "💕")
                }
            }
        }

        println(document)
    }

    "README_ko.md" {
        val document = markdown(GFMFlavor) {
            block {
                +(link("English", "README.md") _ " | "() + "한국어")
            }

            heading(2, "kmarkdown") {
                block {
                    +("kmarkdown은" _ "잘 테스트된"(Bold) + "," _ "순수한 Kotlin"(Bold) _ "Markdown 라이브러리입니다.")
                }
                block {
                    +("이 README.md는 kmarkdown으로 쓰여졌어요." _ link("확인해보세요!", "src/jvmTest/kotlin/io/github/devngho/kmarkdown/Readme.kt"))
                }
            }

            heading(3, "설치") {
                codeblock("""
                |implementation("io.github.devngho:kmarkdown:[VERSION]")
                """.trimMargin(), "kts")
            }

            heading(3, "사용") {
                block {
                    +("kmarkdown을 사용해 마크다운 문서를 만드려면" _ inlineCodeBlock("markdown") _ "함수를 사용하세요.")
                }

                codeblock(
                    """
                |val document = markdown(GFMFlavor) {
                |    // 여기에 마크다운 문서를 작성하세요
                |}
                """.trimMargin(), "kotlin")

                heading(4, "문단") {
                    block {
                        +("문단을 만드려면" _ inlineCodeBlock("paragraph") _ "함수를 사용하세요.")
                    }

                    block {
                        +("문단은 코드 블럭이나 목록과 같은 여러 마크다운 요소를 두 개의 개행으로 구분합니다.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |paragraph {
                    |    block {
                    |        +("이건 문단입니다.")
                    |    }
                    |    block {
                    |        +("여러 블럭을 포함할 수 있습니다.")
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    paragraph {
                        block {
                            +("이건 문단입니다.")
                        }
                        block {
                            +("여러 블럭을 포함할 수 있습니다.")
                        }
                    }
                }

                heading(4, "블럭") {
                    block {
                        +("블럭은 여러 마크다운 요소의 모음입니다.")
                    }

                    block {
                        +("블럭을 만드려면" _ inlineCodeBlock("block") _ "함수를 사용하세요.")
                    }

                    block {
                        +("블럭은 텍스트, 링크와 같은 여러 인라인 마크다운 요소를 포함할 수 있습니다.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |block {
                    |    +("이건 블럭입니다. ")
                    |    +("여러 마크다운 요소를 포함할 수 있습니다.")
                    |    
                    |    // 또는 '_' infix 함수를 사용해 공백으로 마크다운 요소를 연결할 수 있습니다.
                    |    
                    |    +("_ infix 함수를 사용해"() _ "이렇게 블럭을 만들 수도 있습니다."())
                    |    
                    |    // + 연산자는 공백 없이 마크다운 요소를 연결할 수 있습니다.
                    |    
                    |    +("+ 연산자를 사용해 "() + "이렇게 블럭을 만들 수도 있습니다.")
                    |    
                    |    // 또는 마크다운 요소의 리스트를 사용할 수도 있습니다.
                    |    
                    |    +listOf("리스트를 사용해"(), "블럭을 만들 수도 있습니다."())
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    block {
                        +("이건 블럭입니다. ")
                        +("여러 마크다운 요소를 포함할 수 있습니다.")

                        +("+ 연산자를 사용해 "() + "이렇게 블럭을 만들 수도 있습니다.")

                        +("이렇게 블럭을 만들 수도 있습니다."() _ "사용해"())

                        +listOf("리스트를 사용해"(), "블럭을 만들 수도 있습니다."())
                    }

                    blockquote {
                        block {
                            +"텍스트 뒤에 왜 ()를 붙였나요?"(Bold)
                        }
                        block {
                            +("마크다운" _ inlineCodeBlock("Text") + "로 변환하기 위함입니다. () 안에" _ inlineCodeBlock("Bold") + "와 같은 스타일을 추가할 수 있습니다.")
                        }
                    }
                }

                heading(4, "헤딩") {
                    block {
                        +("헤딩을 만드려면" _ inlineCodeBlock("heading") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |heading(2, "Hello, World!") {
                    |    block {
                    |        +("이건 헤딩입니다.")
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    heading(2, "Hello, World!") {
                        block {
                            +("이건 헤딩입니다.")
                        }
                    }
                }

                heading(4, "링크") {
                    block {
                        +("링크를 만드려면" _ inlineCodeBlock("link") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |link("이건 링크입니다.", "https://example.com")
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    link("이건 링크입니다.", "https://example.com")
                }

                heading(4, "리스트") {
                    block {
                        +("리스트를 만드려면" _ inlineCodeBlock("list") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |list(List.ListStyle.ORDERED) {
                    |    item {
                    |        +"리스트 아이템입니다."
                    |    }
                    |    list(List.ListStyle.UNORDERED) {
                    |        item {
                    |            +"중첩된 리스트 아이템입니다."
                    |        }
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    list(List.ListStyle.ORDERED) {
                        item {
                            +"리스트 아이템입니다."
                        }
                        list(List.ListStyle.UNORDERED) {
                            item {
                                +"중첩된 리스트 아이템입니다."
                            }
                        }
                    }
                }

                heading(4, "인용구") {
                    block {
                        +("인용구를 만드려면" _ inlineCodeBlock("blockquote") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |blockquote {
                    |    block {
                    |        +("이건 인용구입니다.")
                    |    }
                    |}
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    blockquote {
                        block {
                            +"이건 인용구입니다."
                        }
                    }
                }

                heading(4, "코드블럭, 인라인 코드블럭") {
                    block {
                        +("코드블럭을 만드려면" _ inlineCodeBlock("codeblock") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |codeblock("println(\"Hello, World!\")", "kotlin")
                    |codeblock("언어 옵션은 선택입니다.")
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    codeblock("println(\"Hello, World!\")", "kotlin")
                    codeblock("언어 옵션은 선택입니다.")

                    block {
                        +("인라인 코드블럭을 만드려면" _ inlineCodeBlock("inlineCodeBlock") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |inlineCodeBlock("println(\"Hello, World!\")")
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    inlineCodeBlock("println(\"Hello, World!\")")
                }

                heading(4, "Raw") {
                    blockquote {
                        block {
                            +("Raw 요소를 사용하는 것은 권장되지 않습니다."(Bold))
                        }
                        block {
                            +("대신에 커스텀" _ inlineCodeBlock("Flavor") + "를 만드는 것을 추천합니다.")
                        }
                    }

                    block {
                        +("Raw 요소를 만드려면" _ inlineCodeBlock("raw") _ "함수를 사용하세요.")
                    }

                    codeblock(
                        """
                    |// 마크다운 문서 안에서 사용하세요
                    |raw("이건 Raw 요소입니다.")
                    """.trimMargin(), "kotlin")

                    block { +("출력:") }

                    raw("이건 Raw 요소입니다.")
                }
            }

            heading(3, "팁") {
                heading(4, "마크다운 이스케이핑") {
                    block {
                        +("기본적으로 텍스트는 이스케이핑됩니다. 단" _ inlineCodeBlock("raw") + "나 코드 블럭은 제외됩니다.")
                    }

                    block {
                        +("이스케이프 대상은 다음과 같습니다: " _ raw("""```\, `, *, _, {, }, [, ], (, ), #, +, -, ., !```"""))
                    }
                }
            }

            heading(3, "라이선스") {
                block {
                    +(link("MIT License", "https://github.com/devngho/kmarkdown/blob/main/LICENSE") _ "💕")
                }
            }
        }

        println(document)
    }
})