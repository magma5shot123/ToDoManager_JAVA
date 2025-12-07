package com.example;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class Controller {

    private GUI gui;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }
    
    public void addTextClick() {
        String task = this.gui.getTextFromTextField();
        if (task == null || task.trim().equals("")) {
            return;
        }
        this.gui.addTask(task);
        this.gui.clearInput();
    }

    public void removeTextClick() {
        this.gui.removeLastTask();
    }

    public void clearAllTextClick() {
        this.gui.removeAllTask();
    }

    public void removeSelectedTask() {
        this.gui.removeSelectedTask();
    }

    public MouseAdapter getListMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JList<String> list = gui.getTaskList();
                    int index = list.locationToIndex(e.getPoint());

                    if (index >= 0) {
                        String oldValue = gui.getTasks().get(index);
                        String newValue = JOptionPane.showInputDialog(
                            null,
                            "Edit task: ",
                            oldValue
                        );

                        if (newValue != null && !newValue.trim().isEmpty()) {
                            gui.getTasks().set(index, newValue);
                            gui.updateTaskList();
                            gui.getStorage().saveTasks(gui.getTasks());
                        } 
                    }
                }
            }
        };
    }
}
