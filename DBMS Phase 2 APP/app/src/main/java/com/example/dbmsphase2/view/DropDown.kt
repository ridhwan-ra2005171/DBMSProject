import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DDMenu(
    items: List<String>,
    selectedItem: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier= Modifier) {
        Text(label, style = TextStyle(
            fontWeight = FontWeight.Bold
        )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(.dp)
//                .height(200.dp)
                .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                .clickable { expanded = true }
                .padding(5.dp)

        ) {
            Text(text =selectedItem.value,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { expanded = true }

            )
        }

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            items.forEach {
                DropdownMenuItem(
                    onClick = {
                        selectedItem.value = it
                        expanded = false
                    },
                    text = { Text(text = it) }
                )
            }
        }

//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            items.forEach { item ->
//                DropdownMenuItem(
//                    onClick = {
//                        selectedItem.value = item
//                        expanded = false
//                    }
//                ) {
//                    Text(text = item)
//                }
//            }
//        }
    }
}



//@Composable
//fun ReusableDropdownMenuExample() {
//    var selectedValue by remember { mutableStateOf("Item 1") }
//
//    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        DDMenu(
//            items = items,
//            selectedItem = remember { mutableStateOf(selectedValue) },
//            label = "Select an item"
//        ){ selectedValue ->
//            selectedValue = selectedValue
//        }
//
//        Text("Selected item: $selectedValue")
//    }
//}

@Preview
@Composable
fun ReusableDropdownMenuExamplePreview() {
//    ReusableDropdownMenuExample()
}