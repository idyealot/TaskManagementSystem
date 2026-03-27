package com.finalproject;

public class Task {
    private String name;
    private String priority;
    private String dueDate;

    public Task(String name, String priority, String dueDate) {
        this.name = name;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public String getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return name + " | " + priority + " | Due: " + dueDate;
    }
}
