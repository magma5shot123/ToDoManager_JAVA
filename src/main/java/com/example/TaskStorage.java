package com.example;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class TaskStorage {

    private static final String FILE_PATH = "tasks.json";
    private Gson gson = new Gson();

    public ArrayList<String> loadTasks() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<String>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveTasks(ArrayList<String> tasks) {
        File file = new File(FILE_PATH);
        ArrayList<String> cleanedTasks = new ArrayList<>();
        for (String t : tasks) {
                if (t != null && !t.trim().isEmpty()) {
                    cleanedTasks.add(t);
                }
            }
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(cleanedTasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
