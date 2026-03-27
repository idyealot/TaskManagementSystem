package com.finalproject;

public class TaskDriver {

    public static void main(String[] args) {

        // Create the queue and stack objects
        TaskQueue taskQueue = new TaskQueue();
        CompletedTaskStack completedStack = new CompletedTaskStack();

        // Create sample tasks
        Task task1 = new Task("Finish project proposal", "High", "04/05/2026");
        Task task2 = new Task("Study for exam", "Medium", "04/10/2026");
        Task task3 = new Task("Buy groceries", "Low", "03/30/2026");

        // Add tasks to the queue
        taskQueue.addTask(task1);
        taskQueue.addTask(task2);
        taskQueue.addTask(task3);

        // Display all tasks currently in the queue
        System.out.println("Current Tasks in Queue:");
        for (Task task : taskQueue.getTasks()) {
            System.out.println(task);
        }

        // Remove the first task and move it to the completed stack
        Task completedTask = taskQueue.removeTask();

        if (completedTask != null) {
            completedStack.completeTask(completedTask);

            System.out.println("\nCompleted Task:");
            System.out.println(completedTask);
        }

        // Show remaining tasks
        System.out.println("\nRemaining Tasks in Queue:");
        for (Task task : taskQueue.getTasks()) {
            System.out.println(task);
        }

        // Show completed tasks
        System.out.println("\nCompleted Tasks in Stack:");
        for (Task task : completedStack.getCompletedTasks()) {
            System.out.println(task);
        }
    }
}
