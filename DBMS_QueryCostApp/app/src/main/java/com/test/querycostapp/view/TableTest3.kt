import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class TableData(
    val tableName: String,
    val rowFormat: String,
    val columnCount: Int,
    val rowCount: Int,
    val avgRowLength: Int,
    val dataLength: Int,
    val blockCount: Int,
    val bfr: Int
)

@Composable
fun DataTable(tableData: List<TableData>) {
    Column {


        LazyRow {
            item {
                // Table header

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    content = {
                        TableHeaderItem("Table Name")
                        TableHeaderItem("Row Format")
                        TableHeaderItem("Column Count")
                        TableHeaderItem("Row Count")
                        TableHeaderItem("Avg Row Length")
                        TableHeaderItem("Data Length")
                        TableHeaderItem("Block Count")
                        TableHeaderItem("BFR")
                    }
                )
            }
            items(tableData) { data ->
                // Table rows
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TableRowItem(data)
                }

            }

        }

    }
}

@Composable
fun TableHeaderItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun TableRowItem(data: TableData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        content = {
            TableDataItem(data.tableName)
            TableDataItem(data.rowFormat)
            TableDataItem(data.columnCount.toString())
            TableDataItem(data.rowCount.toString())
            TableDataItem(data.avgRowLength.toString())
            TableDataItem(data.dataLength.toString())
            TableDataItem(data.blockCount.toString())
            TableDataItem(data.bfr.toString())
        }
    )
}

@Composable
fun TableDataItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ExampleTable() {
    val tableData = remember {
        listOf(
            TableData("EMPLOYEE", "dynamic", 11, 30, 410, 12300, 13, 2),
            TableData("PROJECT", "dynamic", 5, 5, 418, 2090, 3, 1)
        )
    }

    DataTable(tableData)
}

@Preview
@Composable
fun PreviewExampleTable() {
    ExampleTable()
}
