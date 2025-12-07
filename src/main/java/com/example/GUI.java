package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GUI {

    //private final int WIDTH = 300;
    //private final int HEIGHT = 300;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;

    private JButton buttonAdd;
    private JButton buttonRemove;

    private Controller controller;

    private ArrayList<String> tasks;

    private TaskStorage storage = new TaskStorage();


    public GUI(Controller controller) {

        this.tasks = new ArrayList<>();

        // Создание контролерра мышки
        this.controller = controller;
        this.controller.setGUI(this);

        // Создание окна на котором будет панель
        this.frame = new JFrame("TO-Do-List");
        this.frame.setLayout(new BorderLayout());

        // Создание поля где будет весь текст
        this.textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        this.textArea.setEditable(false);
        this.textArea.setRows(10);
        this.textArea.setColumns(30);
        this.frame.add(scroll, BorderLayout.NORTH);

        // Создание input-line
        this.textField = new JTextField();
        this.frame.add(textField, BorderLayout.CENTER);

        // Создание кнопоки для добавления записи

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));


        this.buttonAdd = new JButton("Add");
        this.buttonAdd.addActionListener(e -> {
            controller.addTextClick();
        });
        buttonPanel.add(this.buttonAdd);

        // Создание кнопоки для удаления записи
        this.buttonRemove = new JButton("Remove");
        this.buttonRemove.addActionListener(e -> {
            controller.removeTextClick();
        });
        buttonPanel.add(this.buttonRemove);

        this.frame.add(buttonPanel, BorderLayout.SOUTH);
        
        this.tasks = storage.loadTasks();
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        updateTextArea();

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void clearInput() {
        this.textField.setText("");
    }

    public void addTask(String task) {
        tasks.add(task);
        updateTextArea();
        storage.saveTasks(tasks);
    }

    public void removeLastTask() {
        if (!tasks.isEmpty()) {
            tasks.remove(tasks.size() - 1);
            updateTextArea();
            storage.saveTasks(tasks); 
        }
    }

    private void updateTextArea() {
        this.textArea.setText("");
        for (String t : tasks) {
            this.textArea.append(t + "\n");
        }
    }

    public JTextArea getTextArea() {
        return this.textArea;
    }

    public String getTextFromTextField() {
        return this.textField.getText();
    }
}
