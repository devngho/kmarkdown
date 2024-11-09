[English](README.md)  \|  한국어

## kmarkdown

kmarkdown은 **잘 테스트된**, **순수한 코틀린** 마크다운 라이브러리입니다\.

이 README\.md는 kmarkdown으로 쓰여졌어요\. [확인해보세요\!](src/jvmTest/kotlin/io/github/devngho/kmarkdown/Readme.kt)

### 변경 내역

- 0\.2\.2
  - Italic, GFMStrikethrough element type 수정
- 0\.2\.1
  - Block\.BlockDSL과 MarkdownDSL 생성자는 이제 public입니다\.
- 0\.2\.0
  - MarkdownDSL에 요소를 추가하는 방법이 변경되었습니다\.
  - TextStyle는 이제 일반 MarkdownElement입니다\.
- 0\.1\.0
  - 첫 릴리즈

### 설치

```kts
implementation("io.github.devngho:kmarkdown:[VERSION]")
```

### 사용

kmarkdown을 사용해 마크다운 문서를 만드려면 `markdown` 함수를 사용하세요\.

```kotlin
val document = markdown(GFMFlavor) {
    // 여기에 마크다운 문서를 작성하세요
}
```

#### 문단

문단을 만드려면 `paragraph` 함수를 사용하세요\.

문단은 코드 블럭이나 목록과 같은 여러 마크다운 요소를 두 개의 개행으로 구분합니다\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+paragraph {
    +block {
        +("이건 문단입니다.")
    }
    +block {
        +("여러 블럭을 포함할 수 있습니다.")
    }
}
```

출력:

이건 문단입니다\.

여러 블럭을 포함할 수 있습니다\.

#### 블럭

블럭은 여러 마크다운 요소의 모음입니다\.

블럭을 만드려면 `block` 함수를 사용하세요\.

블럭은 텍스트, 링크와 같은 여러 인라인 마크다운 요소를 포함할 수 있습니다\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+block {
    +("이건 블럭입니다. ")
    +("여러 마크다운 요소를 포함할 수 있습니다.")
    
    // 또는 '_' infix 함수를 사용해 공백으로 마크다운 요소를 연결할 수 있습니다.
    
    +(text("_ infix 함수를 사용해") _ text("이렇게 블럭을 만들 수도 있습니다."))
    
    // + 연산자는 공백 없이 마크다운 요소를 연결할 수 있습니다.
    
    +(text("+ 연산자를 사용해 ") + text("이렇게 블럭을 만들 수도 있습니다."))
    
    // 또는 마크다운 요소의 리스트를 사용할 수도 있습니다.
    
    +listOf(text("리스트를 사용해"), text("블럭을 만들 수도 있습니다."))
}
```

출력:

이건 블럭입니다\. 여러 마크다운 요소를 포함할 수 있습니다\.\_ infix 함수를 사용해 이렇게 블럭을 만들 수도 있습니다\.\+ 연산자를 사용해 이렇게 블럭을 만들 수도 있습니다\.리스트를 사용해 블럭을 만들 수도 있습니다\.

#### 헤딩

헤딩을 만드려면 `heading` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+heading(2, "Hello, World!") {
    +block {
        +("이건 헤딩입니다.")
    }
}
```

출력:

## Hello, World\!

이건 헤딩입니다\.

#### 링크

링크를 만드려면 `link` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+link("이건 링크입니다.", "https://example.com")
```

출력:

[이건 링크입니다\.](https://example.com)

#### 리스트

리스트를 만드려면 `list` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+list(List.ListStyle.ORDERED) {
    item {
        +"리스트 아이템입니다."
    }
    list(List.ListStyle.UNORDERED) {
        item {
            +"중첩된 리스트 아이템입니다."
        }
    }
}
```

출력:

1. 리스트 아이템입니다\.
- 중첩된 리스트 아이템입니다\.

#### 인용구

인용구를 만드려면 `blockquote` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+blockquote {
    +block {
        +("이건 인용구입니다.")
    }
}
```

출력:

> 이건 인용구입니다\.

#### 코드블럭, 인라인 코드블럭

코드블럭을 만드려면 `codeblock` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+codeblock("println(\"Hello, World!\")", "kotlin")
+codeblock("언어 옵션은 선택입니다.")
```

출력:

```kotlin
println("Hello, World!")
```

```
언어 옵션은 선택입니다.
```

인라인 코드블럭을 만드려면 `inlineCodeBlock` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+inlineCodeBlock("println(\"Hello, World!\")")
```

출력:

`println("Hello, World!")`

#### Raw

> **Raw 요소를 사용하는 것은 권장하지 않습니다\.**
>
> 대신에 커스텀 `Flavor`를 만드는 것을 추천합니다\.

Raw 요소를 만드려면 `raw` 함수를 사용하세요\.

```kotlin
// 마크다운 문서 안에서 사용하세요
+raw("이건 Raw 요소입니다.")
```

출력:

이건 Raw 요소입니다.

### 팁

#### 마크다운 이스케이핑

기본적으로 텍스트는 이스케이핑됩니다\. 단 `raw`나 코드 블럭은 제외됩니다\.

이스케이프 대상은 다음과 같습니다:  ```\, `, *, _, {, }, [, ], (, ), #, +, -, ., !```

### 라이선스

[MIT License](https://github.com/devngho/kmarkdown/blob/main/LICENSE) 💕