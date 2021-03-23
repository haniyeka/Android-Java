package com.example.expensemanaging;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ExpenseHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView date;
    public TextView category;
    public TextView amount;
    public TextView note;

    public ImageView deleteExpense;
    public ImageView editExpense;

    public ExpenseHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        date = (TextView)itemView.findViewById(R.id.date);
        category = (TextView)itemView.findViewById(R.id.category);
        amount = (TextView)itemView.findViewById(R.id.amount);
        note = (TextView)itemView.findViewById(R.id.note);

        deleteExpense = (ImageView)itemView.findViewById(R.id.delete_expense);
        editExpense = (ImageView)itemView.findViewById(R.id.edit_expense);
    }
}
