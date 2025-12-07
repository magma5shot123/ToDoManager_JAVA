package com.example;

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

}
