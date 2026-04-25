package com.finalproject;


/*
 * A task management system used to record and
 * track task.  users can add, remove, and complete
 * task.
 * 
* @author Karla Crawley
* @version 1.0
* *  
* OS: windows 11
* IDE: eclipse 4.38.0, 
* Copyright : This is my own original work 
* based on specifications issued by our instructor 
* Academic Honesty: I attest that this is my original work.
* I have not used unauthorized source code, either modified or
* unmodified, nor used generative AI as a final draft. 
* I have not given other fellow student(s) access to my program.
* 
*/

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class TaskManagerGUI extends JFrame {

    private DefaultListModel<String> pendingModel;
    private DefaultListModel<String> completedModel;

    private JList<String> pendingList;
    private JList<String> completedList;

    private JTextField descriptionField;
    private JComboBox<String> priorityBox;
    private JTextField dueDateField;
    
    private static final String TASK_FILE = "tasks.txt";

    public TaskManagerGUI() {

        setTitle("Task Management System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        

        // Top Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton sortPriorityButton = new JButton("Sort by Priority");
        JButton sortDateButton = new JButton("Sort by Due Date");
        JButton exitButton = new JButton("Exit");

        topPanel.add(sortPriorityButton);
        topPanel.add(sortDateButton);
        topPanel.add(exitButton);

        add(topPanel, BorderLayout.NORTH);

        //  Center Panel 
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        // Pending Tasks Panel
        JPanel pendingPanel = new JPanel(new BorderLayout(5, 5));
        pendingPanel.setBorder(BorderFactory.createTitledBorder("Pending Tasks"));

        pendingModel = new DefaultListModel<>();
        pendingList = new JList<>(pendingModel);
        
        pendingList.setCellRenderer(new TaskCellRenderer());

        JScrollPane pendingScroll = new JScrollPane(pendingList);
        pendingPanel.add(pendingScroll, BorderLayout.CENTER);

        JPanel pendingButtonPanel = new JPanel();

        JButton completeButton = new JButton("Mark as Complete");
        JButton removeButton = new JButton("Remove Task");

        pendingButtonPanel.add(completeButton);
        pendingButtonPanel.add(removeButton);

        pendingPanel.add(pendingButtonPanel, BorderLayout.SOUTH);

        // Completed Tasks Panel
        JPanel completedPanel = new JPanel(new BorderLayout(5, 5));
        completedPanel.setBorder(BorderFactory.createTitledBorder("Completed Tasks"));

        completedModel = new DefaultListModel<>();
        completedList = new JList<>(completedModel);
        completedList.setName("completed");
        completedList.setCellRenderer(new TaskCellRenderer());

        JScrollPane completedScroll = new JScrollPane(completedList);
        completedPanel.add(completedScroll, BorderLayout.CENTER);

        centerPanel.add(pendingPanel);
        centerPanel.add(completedPanel);

        add(centerPanel, BorderLayout.CENTER);

        //  Bottom Panel 
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Add New Task"));
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0 - Description
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        bottomPanel.add(new JLabel("Task Description:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        descriptionField = new JTextField(25);
        bottomPanel.add(descriptionField, gbc);
        
        // Cancel Button (top right)
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        JButton cancelButton = new JButton("Cancel");
        bottomPanel.add(cancelButton, gbc);
        
        // Row 1 - Priority
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        bottomPanel.add(new JLabel("Priority:"), gbc);

        gbc.gridx = 1;
        priorityBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});
        bottomPanel.add(priorityBox, gbc);
        
        // Add Button (below Cancel)
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        JButton addButton = new JButton("Add Task");
        bottomPanel.add(addButton, gbc);
        
        // Row 2 - Due Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        bottomPanel.add(new JLabel("Due Date:"), gbc);

        gbc.gridx = 1;
        dueDateField = new JTextField("MM/DD/YYYY");
        bottomPanel.add(dueDateField, gbc);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        

     // Load Saved Tasks 
        loadTasks();
        
     // If there are no saved tasks, add defaults
     if(pendingModel.isEmpty() && completedModel.isEmpty()) {
    	pendingModel.addElement("Finish Project Report (High Priority - Due: 05/25/2026)");
        pendingModel.addElement("Attend Team Meeting (Medium Priority - Due: 05/20/2026)");
        pendingModel.addElement("Read Chapter 5 (Low Priority - Due: 05/22/2026)");
        pendingModel.addElement("Module 11 Assignment (High Priority - Due: 04/07/2026)");

        completedModel.addElement("Submit Assignment");
        completedModel.addElement("Doctor Appointment");
     }

        // Button Actions 

        addButton.addActionListener(e -> addTask());
        
        cancelButton.addActionListener(e -> {
            descriptionField.setText("");
            dueDateField.setText("MM/DD/YYYY");
            priorityBox.setSelectedIndex(0);
        });

        completeButton.addActionListener(e -> {
            int index = pendingList.getSelectedIndex();

            if (index != -1) {
                String task = pendingModel.getElementAt(index);
                completedModel.addElement(task);
                pendingModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a task to mark complete."
                );
            }
        });

        removeButton.addActionListener(e -> {
            int index = pendingList.getSelectedIndex();

            if (index != -1) {
                pendingModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a task to remove."
                );
            }
        });

        sortPriorityButton.addActionListener(e -> sortByPriority());

        sortDateButton.addActionListener(e -> sortByDueDate());

        exitButton.addActionListener(e -> {
        	saveTasks();
        	System.exit(0);
        });
        
        // Save tasks if user clicks the X button
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveTasks();
            }
        });
    }


    private void addTask() {
        String description = descriptionField.getText().trim();
        String priority = (String) priorityBox.getSelectedItem();
        String dueDate = dueDateField.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a task description."
            );
            return;
        }

        try {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MM/dd/uuuu")
                            .withResolverStyle(java.time.format.ResolverStyle.STRICT);

            // Step 1: Parse (this ONLY checks format/valid date)
            LocalDate enteredDate = LocalDate.parse(dueDate, formatter);

            // Step 2: Check for past date
            if (enteredDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this,
                        "Due date cannot be in the past."
                );
                return;
            }

            // Step 3: If valid, create task
            String task = description + " (" + priority
                    + " Priority - Due: " + dueDate + ")";

            pendingModel.addElement(task);

            // Reset fields
            descriptionField.setText("");
            dueDateField.setText("MM/DD/YYYY");
            priorityBox.setSelectedIndex(0);
        } catch (java.time.format.DateTimeParseException ex) {
            // ONLY catches invalid format / impossible dates
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid date in MM/DD/YYYY format."
            );
        }
        
    }
    
    //Sorting by priority
    private void sortByPriority() {
        List<String> tasks = Collections.list(pendingModel.elements());

        tasks.sort((t1, t2) -> {
            int p1 = getPriorityValue(t1);
            int p2 = getPriorityValue(t2);

            if (p1 != p2) {
                return Integer.compare(p1, p2);
            }

            return extractDate(t1).compareTo(extractDate(t2));
        });

        pendingModel.clear();

        for (String task : tasks) {
            pendingModel.addElement(task);
        }
    }

    //Sorting by duedate
    private void sortByDueDate() {
        List<String> tasks = Collections.list(pendingModel.elements());

        tasks.sort((t1, t2) -> extractDate(t1).compareTo(extractDate(t2)));

        pendingModel.clear();

        for (String task : tasks) {
            pendingModel.addElement(task);
        }
    }

    private int getPriorityValue(String task) {
        if (task.contains("High")) {
            return 1;
        } else if (task.contains("Medium")) {
            return 2;
        } else {
            return 3;
        }
    }

    private LocalDate extractDate(String task) {
        try {
            int index = task.indexOf("Due: ");

            if (index != -1) {
                String dateString = task.substring(index + 5, index + 15);

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("MM/dd/yyyy");

                return LocalDate.parse(dateString, formatter);
            }
        } catch (Exception e) {
            // Ignore bad date format
        }

        return LocalDate.MAX;
    }
    
    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TASK_FILE))) {

            for (int i = 0; i < pendingModel.size(); i++) {
                writer.println("PENDING|" + pendingModel.get(i));
            }

            for (int i = 0; i < completedModel.size(); i++) {
                writer.println("COMPLETED|" + completedModel.get(i));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving tasks: " + e.getMessage());
        }
    }
    
    private void loadTasks() {
        File file = new File(TASK_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                
                if (parts.length == 2) {
                    if (parts[0].equals("PENDING")) {
                        pendingModel.addElement(parts[1]);
                    } else if (parts[0].equals("COMPLETED")) {
                        completedModel.addElement(parts[1]);
                    }
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagerGUI gui = new TaskManagerGUI();
            gui.setVisible(true);
        });
    }
    
    class TaskCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            Component c = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            String task = value.toString();

            // Check which list is being rendered
            boolean isCompletedList = list.getName() != null
                    && list.getName().equals("completed");

            if (isCompletedList) {
                // Completed tasks color (gray/blue)
                c.setForeground(new Color(70, 130, 180));
                c.setFont(c.getFont().deriveFont(Font.ITALIC));// gray
            } else {
                // Pending tasks colored by priority
                if (task.contains("High")) {
                    c.setForeground(Color.RED);
                } else if (task.contains("Medium")) {
                    c.setForeground(Color.ORANGE);
                } else if (task.contains("Low")) {
                    c.setForeground(new Color(0, 128, 0));
                }
            }

            // Keep selection visible
            if (isSelected) {
                c.setBackground(new Color(200, 200, 255));
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }
}
   

    	
