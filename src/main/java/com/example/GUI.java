package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GUI {

    private JFrame frame;
    private JTextField textField;

    private DefaultListModel<String> listModel;
    private JList<String> taskList;

    private JButton buttonAdd;
    private JButton buttonRemove;
    private JButton buttonClearAll;

    private Controller controller;

    private ArrayList<String> tasks;

    private TaskStorage storage = new TaskStorage();


    public GUI(Controller controller) {

        // Инициаллизирование списка заданий
        this.tasks = new ArrayList<>();

        // Создание списка с нашими заданиями и окна на котором будет показываться наш список
        this.listModel = new DefaultListModel<>();
        this.taskList = new JList<>(this.listModel);
        this.taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        

        // Создание контролерра мышки
        this.controller = controller;
        this.controller.setGUI(this);

        // Создание окна на котором будет панель
        this.frame = new JFrame("TO-Do-List");
        this.frame.setLayout(new BorderLayout());


        JScrollPane scroll = new JScrollPane(taskList);
        scroll.setPreferredSize(new Dimension(300, 200));
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

        // Создание кнопоки для удаления последней записи или удаления выбраной записи
        this.buttonRemove = new JButton("Remove");
        this.buttonRemove.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index >= 0 ) {
                controller.removeSelectedTask();
                return;
            }
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

        for (String t : tasks) {
            listModel.addElement(t);
        }

        this.taskList.addMouseListener(this.controller.getListMouseAdapter());

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
        listModel.addElement(task);
        storage.saveTasks(tasks);
    }

    // Метод удаления крайней записи
    public void removeLastTask() {
        int lastIndex = this.tasks.size() - 1;
        if (tasks.isEmpty()) {
            return;
        }
        tasks.remove(lastIndex);
        listModel.remove(lastIndex);;
        storage.saveTasks(tasks); 
    }

    public void removeSelectedTask() {
        int index = taskList.getSelectedIndex();

        if (index >= 0) {
            this.tasks.remove(index);
            this.listModel.remove(index);
            this.storage.saveTasks(tasks);
        } else {
            JOptionPane.showMessageDialog(this.frame, "No task selected!");
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
            listModel.clear();
            storage.saveTasks(tasks);
        } else {
            return;
        }
    }

    public JList<String> getTaskList() {
        return this.taskList;
    }

    public ArrayList<String> getTasks() {
        return this.tasks;
    }

    public void updateTaskList() {
        this.taskList.setListData(this.tasks.toArray(new String[0]));
    }

    public TaskStorage getStorage() {
        return this.storage;
    }

    public String getTextFromTextField() {
        return this.textField.getText();
    }
}
