package com.test.querycostapp.view.unusedViews//package com.example.dbmsphase2.view
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.dbmsphase2.model.Employee
////import androidx.lifecycle.viewmodel.compose.viewModel
////import kotlinx.android.synthetic.main.activity_main.*
//import kotlin.random.Random
//
//@Composable
//fun TableExample(persons: List<Person>) {
//    TableHeader()
//    LazyColumn {
//        items(persons) { person ->
//            TableRow(person)
//        }
//    }
//}
//
//@Composable
//fun TableHeader() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text("Name", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//        Text("Age", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//        Text("Email", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//    }
//}
//
//@Composable
//fun TableRow(employee: Employee) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(employee.Fname, fontSize = 16.sp)
//        Text(employee.Minit, fontSize = 16.sp)
//        Text(employee.Fname, fontSize = 16.sp)
//
//    }
//}
//
//data class Person(val name: String, val age: Int, val email: String)
//
//@Composable
//fun TableApp(persons: List<Person>) {
//    MaterialTheme {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            TableExample(persons)
//            Spacer(modifier = Modifier.height(16.dp))
//
//        }
//    }
//}
//
//class MyViewModel : ViewModel() {
//    private val _persons = MutableLiveData(initialData())
//    val persons: LiveData<List<Person>> = _persons
//
//    private fun initialData(): List<Person> {
//        return List(5) {
//            Person("Person $it", Random.nextInt(18, 60), "person$it@example.com")
//        }
//    }
//
//    fun shuffleData() {
//        _persons.value = _persons.value?.shuffled()
//    }
//}
//
//@Preview
//@Composable
//fun previewTableApp() {
////    TableApp(emptyList(), MyViewModel())
////    val viewModel: MyViewModel = viewModel()
//    val sampleData = listOf(
//        Person("Alice", 30, "alice@example.com"),
//        Person("Bob", 25, "bob@example.com"),
//        Person("Charlie", 35, "charlie@example.com"),
//        Person("David", 28, "david@example.com")
//    )
//    TableApp(sampleData)
//}