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
    private JButton buttonClearAll;

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

        
        // Создание панели для кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        // Создание кнопоки для добавления записи
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

        // Создание кнопки очисти всего листа
        this.buttonClearAll = new JButton("Clear All");
        this.buttonClearAll.addActionListener(e -> {
            controller.clearAllTextClick();
        });
        buttonPanel.add(this.buttonClearAll);

        this.frame.add(buttonPanel, BorderLayout.SOUTH);
        
        // Подгрузка тасков из JSON-файла
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

    // Метод очистки поля ввода
    public void clearInput() {
        this.textField.setText("");
    }

    // Метод добавления записи
    public void addTask(String task) {
        tasks.add(task);
        updateTextArea();
        storage.saveTasks(tasks);
    }

    // Метод удаления крайней записи
    public void removeLastTask() {
        if (!tasks.isEmpty()) {
            tasks.remove(tasks.size() - 1);
            updateTextArea();
            storage.saveTasks(tasks); 
        }
    }

    // Метод очистки всех записей
    public void removeAllTask() {
        if (tasks.isEmpty()) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog 
                (frame, 
                "Are you shure?",
                "Confrime delite",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            tasks.clear();
            updateTextArea();
            storage.saveTasks(tasks);
        } else {
            return;
        }
    }

    // Метод обновления записей
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
