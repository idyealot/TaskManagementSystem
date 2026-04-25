package com.finalproject;

import java.util.Objects;

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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task)) return false;

        Task other = (Task) obj;

        return Objects.equals(name, other.name) &&
               Objects.equals(priority, other.priority) &&
               Objects.equals(dueDate, other.dueDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, priority, dueDate);
    }
}
