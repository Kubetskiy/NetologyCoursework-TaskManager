package ru.netology.javacore;

import java.util.*;

/**
 Сам список дел храним в toDoList
 Историю операций храним в стеке operationStack
 */
public class Todos {

    private static final int MAXIMUM_TASKS = 7;
    private final TreeSet<String> toDoList = new TreeSet<>();
    private final Deque<Pair> operationStack = new LinkedList<>();

    /**
     @param task Название добавляемой задачи<br>
     В случае успешного добавления (было место в списке), операция добавляется в стек
     */
    public void addTask(String task) {
        if (addTaskWithoutWritingToTheStack(task)) {
            operationStack.push(new Pair(Operation.ADD, task));
        }
    }

    /**
     Проверяем наличие места в списке, добавляем, возвращаем тру, если место было
     * @param task
     * @return
     */
    private boolean addTaskWithoutWritingToTheStack(String task) {
        if (toDoList.size() < MAXIMUM_TASKS) {
            toDoList.add(task);
            return true;
        }
        return false;
    }

    /**
     @param task Название удаляемой задачи.<br>
     В случае успешного удаления операция добавляется в стек
     */
    public void removeTask(String task) {
        if (removeTaskWithoutWritingToTheStack(task)) {
            operationStack.push(new Pair(Operation.REMOVE, task));
        }
    }

    /**
     Проверяем наличие задачи, удаляем и возвращаем тру, если она была
     * @param task
     * @return
     */
    private boolean removeTaskWithoutWritingToTheStack(String task) {
        if (toDoList.contains(task)) {
            toDoList.remove(task);
            return true;
        }
        return false;
    }

    /**
     Достаем из стека пару операция - задача<br>
     Вызываем обратную операцию для задачи
     */
    public void restoreTask() {
        System.out.println(" ");
        if (!operationStack.isEmpty()) {
            var pair = operationStack.pop();
            switch (pair.operation) {
                case REMOVE -> addTaskWithoutWritingToTheStack(pair.task);
                case ADD -> removeTaskWithoutWritingToTheStack(pair.task);
            }
        }
    }

    /**
     @return Возвращает список дел, разделенных пробелами, в одну строку
     */
    public String getAllTasks() {
        var stringBuilder = new StringBuilder();
        for (String s : toDoList) {
            stringBuilder.append(s);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }

    /**
     Вспомогательный класс для хранения пар операция - задача в стеке
     */
    private class Pair {
        Operation operation;
        String task;

        public Pair(Operation operation, String task) {
            this.operation = operation;
            this.task = task;
        }
    }
}
