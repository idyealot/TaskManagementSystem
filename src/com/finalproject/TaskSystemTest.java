package com.finalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class TaskSystemTest {

    private Task task1, task2, task3;
    private TaskQueue taskQueue;
    private CompletedTaskStack completedStack;

    @BeforeEach
    public void setUp() {
        // Create sample tasks
        task1 = new Task("Finish project report", "High", "04/10/2026");
        task2 = new Task("Buy groceries", "Medium", "04/06/2026");
        task3 = new Task("Workout", "Low", "04/05/2026");

        // Initialize queue and stack
        taskQueue = new TaskQueue();
        completedStack = new CompletedTaskStack();
    }

    //  Task Tests
    @Test
    public void testTaskGetters() {
        assertEquals("Finish project report", task1.getName());
        assertEquals("High", task1.getPriority());
        assertEquals("04/10/2026", task1.getDueDate());
    }

    @Test
    public void testTaskToString() {
        String expected = "Finish project report | High | Due: 04/10/2026";
        assertEquals(expected, task1.toString());
    }

    //  TaskQueue Tests 
    @Test
    public void testAddAndRemoveTaskQueue() {
        taskQueue.addTask(task1);
        taskQueue.addTask(task2);

        Queue<Task> tasks = taskQueue.getTasks();
        assertEquals(2, tasks.size());

        Task removed = taskQueue.removeTask();
        assertEquals(task1, removed); // FIFO
        assertEquals(1, taskQueue.getTasks().size());
    }

    @Test
    public void testEmptyQueueRemove() {
        assertNull(taskQueue.removeTask(), "Removing from empty queue should return null");
    }

    // CompletedTaskStack Tests 
    @Test
    public void testCompleteTaskAndStackOrder() {
        completedStack.completeTask(task1);
        completedStack.completeTask(task2);

        Stack<Task> stack = completedStack.getCompletedTasks();
        assertEquals(2, stack.size());

        // LIFO: top of stack should be last completed
        assertEquals(task2, stack.peek());
    }

    @Test
    public void testMoveTaskFromQueueToStack() {
        taskQueue.addTask(task3);

        Task removed = taskQueue.removeTask();
        completedStack.completeTask(removed);

        assertEquals(0, taskQueue.getTasks().size());
        assertEquals(task3, completedStack.getCompletedTasks().peek());
    }
    
    @Test
    public void testInvalidTaskCreation() {
        Task badTask = new Task("", "High", "04/10/2026");
        assertEquals("", badTask.getName());
    }
    
    @Test
    public void testInvalidPriority() {
        Task task = new Task("Test", "Super High", "04/10/2026");
        assertNotEquals("High", task.getPriority()); // or expect exception
    }
    
    @Test
    public void testInvalidDateFormat() {
        Task task = new Task("Test", "High", "2026-04-10");
        assertEquals("2026-04-10", task.getDueDate());
    }
    
    @Test
    public void testEmptyStackPeek() {
        assertTrue(completedStack.getCompletedTasks().isEmpty());
    }
    
    @Test
    public void testQueueOrderMultipleRemovals() {
        taskQueue.addTask(task1);
        taskQueue.addTask(task2);
        taskQueue.addTask(task3);

        assertEquals(task1, taskQueue.removeTask());
        assertEquals(task2, taskQueue.removeTask());
        assertEquals(task3, taskQueue.removeTask());
    }
    
    
    
    @Test
    public void testFullTaskLifecycle() {
        taskQueue.addTask(task1);

        Task t = taskQueue.removeTask();
        completedStack.completeTask(t);

        assertTrue(taskQueue.getTasks().isEmpty());
        assertEquals(task1, completedStack.getCompletedTasks().peek());
    }
    
   
    
}