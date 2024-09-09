package io.github.devngho.kmarkdown.flavor.common

import io.github.devngho.kmarkdown.builder.MarkdownDSL
import io.github.devngho.kmarkdown.builder.MarkdownDSLMarker
import io.github.devngho.kmarkdown.flavor.Flavor
import io.github.devngho.kmarkdown.flavor.Flavor.Companion.convertTo
import io.github.devngho.kmarkdown.flavor.MarkdownElement
import io.github.devngho.kmarkdown.flavor.MarkdownElementDescriptor
import kotlin.collections.List as KList

@MarkdownDSLMarker
data class Table(val header: TableRow, val rows: KList<TableRow>): MarkdownElement {
    override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = Table

    enum class TableColOrder {
        LEFT,
        CENTER,
        RIGHT,
        NONE
    }

    @MarkdownDSLMarker
    data class TableRow(val cols: KList<MarkdownElement>): MarkdownElement {
        override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = TableRow

        override fun encode(): String = throw UnsupportedOperationException("Table rows cannot be encoded")

        companion object: MarkdownElementDescriptor<TableRow> {
            override val id: String = "table.row"
            override val flavor: Flavor = CommonFlavor

            override fun convertToFlavor(element: TableRow, flavor: Flavor): MarkdownElement {
                return TableRow(element.cols.map { it.convertTo(flavor) })
            }
            override fun convertFromElement(element: MarkdownElement): TableRow? = null
        }
    }

    @MarkdownDSLMarker
    data class TableCol(val item: MarkdownElement): MarkdownElement {
        override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = TableCol

        override fun encode(): String = throw UnsupportedOperationException("Table cols cannot be encoded")

        companion object: MarkdownElementDescriptor<TableCol> {
            override val id: String = "table.col"
            override val flavor: Flavor = CommonFlavor

            override fun convertToFlavor(element: TableCol, flavor: Flavor): MarkdownElement {
                return TableCol(element.item.convertTo(flavor))
            }
            override fun convertFromElement(element: MarkdownElement): TableCol? = null
        }
    }

    @MarkdownDSLMarker
    data class TableColOrdered(val item: MarkdownElement, val order: TableColOrder): MarkdownElement {
        override val descriptor: MarkdownElementDescriptor<out MarkdownElement> = TableColOrdered

        override fun encode(): String = throw UnsupportedOperationException("Table cols cannot be encoded")

        companion object: MarkdownElementDescriptor<TableColOrdered> {
            override val id: String = "table.col.ordered"
            override val flavor: Flavor = CommonFlavor

            override fun convertToFlavor(element: TableColOrdered, flavor: Flavor): MarkdownElement {
                return TableColOrdered(element.item.convertTo(flavor), element.order)
            }
            override fun convertFromElement(element: MarkdownElement): TableColOrdered? = when (element) {
                is TableCol -> TableColOrdered(element.item, TableColOrder.NONE)
                else -> null
            }
        }
    }

    @MarkdownDSLMarker
    class TableDSL internal constructor(val flavor: Flavor) {
        private var headers: TableRow? = null
        private val rows = mutableListOf<TableRow>()

        fun header(block: TableRowOrderedDSL.() -> Unit) {
            val header = TableRowOrderedDSL(flavor).apply(block).build()
            headers = header
        }

        fun row(block: TableRowDSL.() -> Unit) {
            val row = TableRowDSL(flavor).apply(block).build()
            rows.add(row)
        }

        fun build(): Table {
            if (headers == null) throw IllegalArgumentException("Table must have a header")

            return Table(headers!!, rows)
        }
    }

    @MarkdownDSLMarker
    class TableRowDSL internal constructor(val flavor: Flavor) {
        private val cols = mutableListOf<MarkdownElement>()

        fun col(block: Block.BlockDSL.() -> Unit) {
            val col = Block(Block.BlockDSL(flavor).apply(block).build())
            cols.add(TableCol(col))
        }

        fun build(): TableRow = TableRow(cols)
    }

    @MarkdownDSLMarker
    class TableRowOrderedDSL internal constructor(val flavor: Flavor) {
        private val cols = mutableListOf<MarkdownElement>()

        fun col(order: TableColOrder = TableColOrder.NONE,  block: Block.BlockDSL.() -> Unit) {
            val col = Block(Block.BlockDSL(flavor).apply(block).build())
            cols.add(TableColOrdered(col, order))
        }

        fun build(): TableRow = TableRow(cols)
    }

    override fun encode(): String {
        val headerText = header.cols.joinToString("|") { when(it) {
            is TableCol -> it.item.encode()
            is TableColOrdered -> it.item.encode()
            else -> throw IllegalArgumentException("Invalid table col type")
        } }
        val separatorText = header.cols.joinToString("|") {
            when (it) {
                is TableCol -> "---"
                is TableColOrdered -> when (it.order) {
                    TableColOrder.LEFT -> ":---"
                    TableColOrder.CENTER -> ":---:"
                    TableColOrder.RIGHT -> "---:"
                    TableColOrder.NONE -> "---"
                }

                else -> throw IllegalArgumentException("Invalid table col type")
            }
        }

        val bodyText = rows.joinToString("\n") { row ->
            "|" + row.cols.joinToString("|") { when(it) {
                is TableCol -> it.item.encode()
                is TableColOrdered -> it.item.encode()
                else -> throw IllegalArgumentException("Invalid table col type")
            } } + "|"
        }

        return "|$headerText|\n|$separatorText|\n$bodyText"
    }

    companion object: MarkdownElementDescriptor<Table> {
        fun MarkdownDSL.table(block: TableDSL.() -> Unit) {
            val element = TableDSL(this.flavor).apply(block).build()
            add(element)
        }

        override val id: String = "table"
        override val flavor: Flavor = CommonFlavor

        override fun convertToFlavor(element: Table, flavor: Flavor): MarkdownElement {
            return Table(element.header.convertTo(flavor) as TableRow, element.rows.map { it.convertTo(flavor) as TableRow })
        }

        override fun convertFromElement(element: MarkdownElement): Table? = null
    }
}