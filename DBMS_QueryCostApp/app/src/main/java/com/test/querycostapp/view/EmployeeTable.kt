package com.example.dbmsphase2.view

//import androidx.lifecycle.viewmodel.compose.viewModel
//import kotlinx.android.synthetic.main.activity_main.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.querycostapp.model.Employee
import com.test.querycostapp.repo.EmployeeRepo

@Composable
fun TableEmployee(employees: List<Employee>) {
    TableHeaderEmployee()
    LazyColumn {
        items(employees) { employee ->
            TableRowEmployee(employee)
        }
    }
}

@Composable
fun TableHeaderEmployee() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("SSN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Fname", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Minit", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Lname", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("DOB", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Address", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Gender", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("PhoneNo", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("HireDate", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Manager", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("ManagerSSN", fontSize = 18.sp, fontWeight = FontWeight.Bold)

    }
}

@Composable
fun TableRowEmployee(employee: Employee) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(employee.SSN, fontSize = 16.sp)
        Text(employee.Fname, fontSize = 16.sp)
        Text(employee.Minit, fontSize = 16.sp)
        Text(employee.Lname, fontSize = 16.sp)
        Text(employee.DOB, fontSize = 16.sp)
        Text(employee.Address, fontSize = 16.sp)
        Text(employee.Gender, fontSize = 16.sp)
        Text(employee.PhoneNo, fontSize = 16.sp)
        Text(employee.HireDate, fontSize = 16.sp)
        Text(employee.Manager.toString(), fontSize = 16.sp)
        Text(employee.ManagerSSN.toString(), fontSize = 16.sp)


    }
}

//data class Empl(val name: String, val age: Int, val email: String)

@Composable
fun TableAppEmployee(employees: List<Employee>) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TableEmployee(employees)
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

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

@Preview
@Composable
fun previewTableApp() {
//    TableApp(emptyList(), MyViewModel())
//    val viewModel: MyViewModel = viewModel()
    val employees = EmployeeRepo.initEmployees(LocalContext.current)



//    TableApp(sampleData)
    TableAppEmployee(employees)
}


//@Preview
//@Composable
//fun previewEmployeeList(){
//    val employees = EmployeeRepo.initEmployees(LocalContext.current)
//
//    Log.d("test", "previewEmployeeList: "+employees.size)
//}