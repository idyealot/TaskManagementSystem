package com.finalproject;


import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private Queue<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask() {
        return tasks.poll();
    }
    
    public Queue<Task> getTasks() {
        return tasks;
    }
}
