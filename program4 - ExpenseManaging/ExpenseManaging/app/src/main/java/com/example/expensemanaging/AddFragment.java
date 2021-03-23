package com.example.expensemanaging;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public class AddFragment extends Fragment{

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }
    private SqliteHelper mDatabase;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText namefield = (EditText)view.findViewById(R.id.name);
        final EditText amountfield = (EditText)view.findViewById(R.id.amount);
        final EditText notefield = (EditText)view.findViewById(R.id.note);
        final AutoCompleteTextView categoryfield = (AutoCompleteTextView)view.findViewById(R.id.category);
        final EditText datefield = (EditText)view.findViewById(R.id.date);
        mDatabase = new SqliteHelper((MainActivity) this.getContext());
        List<String> categories = mDatabase.selectCategories();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item,categories);
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
        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        view.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = namefield.getText().toString();
                float amount = -1;
                if(!TextUtils.isEmpty(amountfield.getText().toString())) {
                    amount = Float.parseFloat(amountfield.getText().toString());
                }
                final String category = categoryfield.getText().toString();
                final String note = notefield.getText().toString();
                final String date = datefield.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(category) || amount <= 0 || TextUtils.isEmpty(date) ){
                    Toast.makeText(getContext(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Expense newExpense = new Expense(name, category, date, amount, note);
                    mDatabase.addExpense(newExpense);
                    NavHostFragment.findNavController(AddFragment.this)
                          .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }

            }
        });
    }
}