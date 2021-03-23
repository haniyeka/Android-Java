package com.example.expensemanaging;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseHolder>{

    private Context context;
    private List<Expense> listProducts;
    private SqliteHelper mDatabase;

    public ExpenseAdapter(Context context, List<Expense> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteHelper((MainActivity) context);
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_layout, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExpenseHolder holder, final int position) {
        final Expense singleProduct = listProducts.get(position);

        holder.name.setText(singleProduct.getName());
        holder.category.setText(singleProduct.getCategory());
        holder.date.setText(singleProduct.getDate());
        holder.amount.setText("$"+Float.toString(singleProduct.getAmount()));
        holder.editExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteExpense(singleProduct.getId());
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    private void editTaskDialog(final Expense product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.edit_layout, null);

        final EditText namefield = (EditText)subView.findViewById(R.id.name);
        final EditText amountfield = (EditText)subView.findViewById(R.id.amount);
        final EditText notefield = (EditText)subView.findViewById(R.id.note);
        final AutoCompleteTextView categoryfield = (AutoCompleteTextView)subView.findViewById(R.id.category);
        final EditText datefield = (EditText)subView.findViewById(R.id.date);
        List<String> categories = mDatabase.selectCategories();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,categories);
        categoryfield.setThreshold(1);
        categoryfield.setAdapter(adapter);
        categoryfield.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                categoryfield.showDropDown();
                categoryfield.requestFocus();

                return true;
            }
        });
        categoryfield.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //categoryfield.showDropDown();
                if(hasFocus)
                categoryfield.setText("");
            }
        });
        if(product != null){
            namefield.setText(product.getName());
            amountfield.setText(String.valueOf(product.getAmount()));
            notefield.setText((product.getNote()));
            categoryfield.setText((product.getCategory()));
            datefield.setText((product.getDate()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = namefield.getText().toString();
                final String category = categoryfield.getText().toString();
                float amount = -1;
                if(!TextUtils.isEmpty(amountfield.getText().toString()))
                {
                    amount = Float.parseFloat(amountfield.getText().toString());
                }
                final String note = notefield.getText().toString();
                final String date = datefield.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(category) || TextUtils.isEmpty(date) || amount <= 0){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateExpense(new Expense(product.getId(), name , category , date , amount, note));
                    ((Activity)context).finish();
                    context.startActivity(((Activity) context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
