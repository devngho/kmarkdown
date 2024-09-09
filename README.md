English \| [한국어](README_ko.md)

## kmarkdown

kmarkdown is a **well\-tested** and **pure\-Kotlin** Markdown library\.

This README\.MD was generated using kmarkdown\. [Check it out\!](https://github.com/devngho/kmarkdown/src/jvmTest/kotlin/io/github/devngho/kmarkdown/Readme.kt)

### Installation

```kts
implementation("io.github.devngho:kmarkdown:[VERSION]")
```

### Usage

To use kmarkdown, you can create a markdown document using the `markdown` function\.

The `markdown` function takes a Flavor as a parameter \(optional\) and returns a markdown document\.

```kotlin
val document = markdown(GFMFlavor) {
    // Your markdown document here
}
```

#### Paragraph

To create a paragraph, you can use the `paragraph` function\.

The `paragraph` function takes a block as a parameter\.

A paragraph represents multiple markdown elements, such as code blocks or list, separated by two newline\.

```kotlin
// in markdown document
paragraph {
    block {
        +("This is a paragraph.")
    }
    block {
        +("It can contain multiple blocks.")
    }
}
```

Output:

This is a paragraph\.

It can contain multiple blocks\.

#### Block

A block is a collection of markdown elements\.

You can create a block using the `block` function\.

A block can contain multiple inline markdown elements like text, link\.

```kotlin
// in markdown document
block {
    +("This is a block. ")
    +("It can contain multiple markdown elements.")
    
    // or you can use '_' infix function
    // to concatenate markdown elements with a space
    
    +("This is another way to create a block."() _ "using the '_' infix function."())
    
    // + operator can concatenate markdown elements without space
    
    +("+ operator can concatenate "() + "markdown elements without space.")
    
    // or you can use list of markdown elements
    
    +listOf("You can also use a list of markdown elements."(), "to create a block.")
}
```

Output:

This is a block\. It can contain multiple markdown elements\.This is another way to create a block\. using the '\_' infix function\.\+ operator can concatenate markdown elements without space\.You can also use a list of markdown elements\. to create a block\.

> **Why did you append \(\) to the text?**
>
> Because the text is a markdown element, and the function expects a markdown element\. You can add styles to the text by appending styles to the text\.

#### Heading

To create a heading, you can use the `heading` function\.

The `heading` function takes a level and text as parameters\.

```kotlin
// in markdown document
heading(2, "Hello, World!") {
    block {
        +("This is a heading.")
    }
}
```

Output:

## Hello, World\!

This is a heading\.

#### Link

To create a link, you can use the `link` function\.

The `link` function takes a text and url as parameters\.

```kotlin
// in markdown document
link("This is a link", "https://example.com")
```

Output:

[This is a link](https://example.com)

#### List

To create a list, you can use the `list` function\.

The `list` function takes a list style as a parameter\.

```kotlin
// in markdown document
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
```

Output:

1. This is a list item\.
- This is a nested list item\.

#### Blockquote

To create a blockquote, you can use the `blockquote` function\.

The `blockquote` function takes a block as a parameter\.

```kotlin
// in markdown document
blockquote {
    block {
        +("This is a blockquote.")
    }
}
```

Output:

> This is a blockquote\.

#### CodeBlock and InlineCodeBlock

To create a code block, you can use the `codeblock` function\.

The `codeblock` function takes a code block and language \(optional\) as parameters\.

```kotlin
// in markdown document
codeblock("println(\"Hello, World!\")", "kotlin")
codeblock("Language is optional.")
```

Output:

```kotlin
println("Hello, World!")
```

```
Language is optional.
```

To create an inline code block, you can use the `inlineCodeBlock` function\.

The `inlineCodeBlock` function takes a code block as a parameter\.

```kotlin
// in markdown document
inlineCodeBlock("println(\"Hello, World!\")")
```

Output:

`println("Hello, World!")`

#### Raw

> **It's not recommended to use raw elements\.**
>
> I recommend creating your custom flavor instead\.

To create a raw element, you can use the `raw` function\.

The `raw` function takes a text as a parameter\.

```kotlin
// in markdown document
raw("This is a raw element.")
```

Output:

This is a raw element.

### Tips

#### Escape Markdown

Your text will be escaped by default except you use `raw`  or code blocks\.

The escape characters are: ```\, `, *, _, {, }, [, ], (, ), #, +, -, ., !```

### License

[MIT License](https://github.com/devngho/kmarkdown/blob/main/LICENSE) 💕