package io.github.devngho.kmarkdown

import io.github.devngho.kmarkdown.builder.MarkdownDSL.Companion.markdown
import io.github.devngho.kmarkdown.flavor.common.Blockquote.Companion.blockquote
import io.github.devngho.kmarkdown.flavor.common.Text.Companion.text
import io.github.devngho.kmarkdown.flavor.common.Bold
import io.github.devngho.kmarkdown.flavor.common.CodeBlock.Companion.codeblock
import io.github.devngho.kmarkdown.flavor.common.Heading.Companion.heading
import io.github.devngho.kmarkdown.flavor.common.Italic
import io.github.devngho.kmarkdown.flavor.common.Paragraph.Companion.paragraph
import io.github.devngho.kmarkdown.flavor.common.Block.Companion.block
import io.github.devngho.kmarkdown.flavor.common.HorizontalRule.Companion.horizontalRule
import io.github.devngho.kmarkdown.flavor.common.InlineCodeBlock.Companion.inlineCodeBlock
import io.github.devngho.kmarkdown.flavor.common.Link.Companion.link
import io.github.devngho.kmarkdown.flavor.common.List
import io.github.devngho.kmarkdown.flavor.common.List.Companion.list
import io.github.devngho.kmarkdown.flavor.common.Raw.Companion.raw
import io.github.devngho.kmarkdown.flavor.common.Table
import io.github.devngho.kmarkdown.flavor.common.Table.Companion.table
import io.github.devngho.kmarkdown.flavor.gfm.GFMFlavor
import io.github.devngho.kmarkdown.flavor.gfm.GFMStrikethrough
import io.github.devngho.kmarkdown.flavor.gfm.GFMTaskList.Companion.gfmTaskList
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BuilderTest: StringSpec({
    "Can build a markdown document" {
        val document = markdown {
            paragraph {
                block {
                    +("Hello, World!" _ "Hello, World!".styled(Bold) _ "Hello, World!".styled(Bold, Italic))

                    +listOf(
                        inlineCodeBlock("println(\"Hello, World!\")"),
                        link("Example site", "https://example.com"),
                        link("https://example.com") { +"Example site".styled(Bold) }
                    )
                }
            }

            paragraph {
                list(List.ListStyle.ORDERED) {
                    item {
                         +"This is a list item."
                    }
                    item {
                        +raw("This is another list item.")
                    }
                    list(List.ListStyle.UNORDERED) {
                        item {
                            +"This is a nested list item."
                        }
                        item {
                            +link("This is a nested list item.", "https://example.com")
                        }
                        list(List.ListStyle.UNORDERED) {
                            item {
                                +"This is a nested list in a nested list item."
                            }
                        }
                    }
                }

                table {
                    header {
                        col { +"Header 1" }
                        col(Table.TableColOrder.LEFT) { +"Header 2" }
                        col(Table.TableColOrder.CENTER) { +"Header 3" }
                        col(Table.TableColOrder.RIGHT) { +"Header 4" }
                    }

                    row {
                        col { +"Row 1, Col 1" }
                        col { +"Row 1, Col 2" }
                        col { +"Row 1, Col 3" }
                        col { +"Row 1, Col 4" }
                    }

                    row {
                        col { +"Row 2, Col 1" }
                        col { +"Row 2, Col 2" }
                        col { +"Row 2, Col 3" }
                        col { +"Row 2, Col 4" }
                    }
                }
            }

            horizontalRule()

            paragraph {
                link("This is a link", "https://example.com")
                link("https://example.com") {
                    +"This is also a link".styled(Bold)
                }
                raw("This is a raw element.")

                inlineCodeBlock("println(\"Hello, World!\")")
            }

            heading(2, "Hello, World!") {
                blockquote {
                    text("This is a blockquote.")
                    text("With multiple lines.", Bold)
                    list(List.ListStyle.UNORDERED) {
                        item {
                            +"List in blockquote."
                        }
                    }
                }

                codeblock("""
                |A code block.
                """.trimMargin())

                codeblock("""
                |fun main() {
                |    println("Hello, World!")
                |}
                """.trimMargin(), "kotlin")
            }

//            paragraph {
//                text("This is a paragraph.")
//            }
//
//            list {
//                item {
//                    text("This is a list item.")
//                }
//            }
        }

        document shouldBe """
        |Hello, World\! **Hello, World\!** ***Hello, World\!***`println("Hello, World!")` [Example site](https://example.com) [**Example site**](https://example.com)
        |
        |1. This is a list item\.
        |2. This is another list item.
        |  - This is a nested list item\.
        |  - [This is a nested list item\.](https://example.com)
        |    - This is a nested list in a nested list item\.
        |
        ||Header 1|Header 2|Header 3|Header 4|
        ||---|:---|:---:|---:|
        ||Row 1, Col 1|Row 1, Col 2|Row 1, Col 3|Row 1, Col 4|
        ||Row 2, Col 1|Row 2, Col 2|Row 2, Col 3|Row 2, Col 4|
        |
        |---
        |
        |[This is a link](https://example.com)
        |
        |[**This is also a link**](https://example.com)
        |
        |This is a raw element.
        |
        |`println("Hello, World!")`
        |
        |## Hello, World\!
        |
        |> This is a blockquote\.
        |> 
        |> **With multiple lines\.**
        |> 
        |> - List in blockquote\.
        |
        |```
        |A code block.
        |```
        |
        |```kotlin
        |fun main() {
        |    println("Hello, World!")
        |}
        |```
        """.trimMargin()
    }

    "Can build a markdown document (GFM flavor)" {
        val document = markdown(GFMFlavor) {
            text("Hello, World!", GFMStrikethrough)

            gfmTaskList {
                item(false) {
                    +"This is a task list item."
                }
                item(true) {
                    +"This is a completed task list item."
                }
                gfmTaskList {
                    item(false) {
                        +"This is a nested task list item."
                    }
                    item(true) {
                        +"This is a completed nested task list item."
                    }
                    gfmTaskList {
                        item(false) {
                            +"This is a nested task list in a nested task list item."
                        }
                    }
                }
            }
        }

        document shouldBe """
        |~~Hello, World\!~~
        |
        |- [ ] This is a task list item\.
        |- [x] This is a completed task list item\.
        |  - [ ] This is a nested task list item\.
        |  - [x] This is a completed nested task list item\.
        |    - [ ] This is a nested task list in a nested task list item\.
        """.trimMargin()
    }
})