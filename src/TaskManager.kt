import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`) //Scanner for user input
    val taskManager = TaskManager() //Creating an instance of TaskManager
    var firstRun = true //Tracking to see if it's the first run

    while (true) {
       //Displaying welcome message for the first run only
        if (firstRun) {
           println("-----------------------------")
           println("Welcome to the Task Manager!")
           firstRun = false
       }
        //Menu options
       println("TASK MANAGER MENU")
       println("----------------------------")
       println("1. Add a task")
       println("2. Remove a task")
       println("3. Check off a task")
       println("4. Display task list")
       println("5. Exit")
       println("----------------------------------------------------")
       println("Please input a valid number from the choices above: ")

        //Get user input (converted to int, if invalid returned as null)
       val choice = scanner.nextLine().toIntOrNull()

       when (choice) {
           //Adding a new task
           1 -> {
               print("What task would you like to add: ")
               val description = scanner.nextLine()
               taskManager.addTask(description)
           }
           //Removing a task
           2 -> {
               taskManager.displayTasks() //display current list
               print("Enter task number to remove: ")
               val id = scanner.nextLine().toIntOrNull()
               if (id != null) taskManager.removeTask(id) else println("Error. Please enter a valid number.")
           }
           //Checking off a task
           3 -> {
               taskManager.displayTasks() //display current list
               print("Enter task number to check off: ")
               val id = scanner.nextLine().toIntOrNull()
               if (id != null) taskManager.checkOffTask(id) else println("Error. Please enter a valid number.")
           }
           //Displaying tasks
           4 -> taskManager.displayTasks()
           //Exiting the program & breaking the loop
           5 -> {
               println("See you soon!")
               break
           }
           else -> println("Error. Please choose a number 1-5.")
       }
   }
}

//Gives task an ID, the task itself, and a check or blank space to indicate completion
data class Task(val id: Int, val description: String, var isCompleted: Boolean = false)

class TaskManager {
    private val tasks = mutableListOf<Task>()
    private var nextId = 1 //ID counter starting at 1

    //Adding a new task
    fun addTask(description: String) {
        tasks.add(Task(nextId++, description))
        println("✓ Task successfully added!")
    }

    //Removing a task
    fun removeTask(id: Int) {
        val taskToRemove = tasks.find { it.id == id } //Finding task by ID
        if (taskToRemove != null) {
            tasks.remove(taskToRemove)

            //Renumber tasks as they get removed
            for (i in tasks.indices) {
                tasks[i] = tasks[i].copy(id = i + 1)
            }

            println("Task successfully removed.")
        } else {
            println("Task not found. Please enter a valid task number.")
        }
    }

    //Marking a task as completed
    fun checkOffTask(id: Int) {
        val taskToCheck = tasks.find { it.id == id} //Finding task by ID
        if (taskToCheck != null) {
            taskToCheck.isCompleted = true
            println("✓ Task successfully completed!")
        } else {
            println("Task not found. Please enter a valid task number.")
        }
    }

    //Display current task list
    fun displayTasks() {
        if (tasks.isEmpty()) {
            println("No tasks available. Add tasks to view them.")
        } else {
            println("Tasks:")
            for (task in tasks) {
                val status = if (task.isCompleted) "(✓)" else "( )"
                println("${task.id}. $status - ${task.description}")
            }
        }
    }
}

