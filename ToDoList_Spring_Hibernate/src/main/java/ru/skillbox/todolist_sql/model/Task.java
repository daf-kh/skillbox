package ru.skillbox.todolist_sql.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int importance;
    private boolean done;

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }


    public Task(String name, int importance, boolean done) {
        this.name = name;
        this.importance = importance;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", importance=" + importance +
                ", isDone=" + done +
                '}';
    }
}
