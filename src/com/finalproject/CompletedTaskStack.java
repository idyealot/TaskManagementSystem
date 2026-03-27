package com.finalproject;

import java.util.Stack;

public class CompletedTaskStack {
    private Stack<Task> completed = new Stack<>();

    public void completeTask(Task task) {
        completed.push(task);
    }

    public Stack<Task> getCompletedTasks() {
        return completed;
    }
}
