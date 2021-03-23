package com.example.expensemanaging;

public class Expense {
    private	int	id;
    private	String name;
    private	float amount;
    private String date;
    private String note;
    private String category;

    public Expense(String name, String category, String date, float amount, String note) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.category = category;
    }

    public Expense(int id, String name, String category, String date, float amount, String note) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.category = category;
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

    public float getAmount() {
        return amount;
    }
    public String getNote() {
        return note;
    }
    public String getDate() {
        return date;
    }
    public String getCategory() {
        return category;
    }
}
