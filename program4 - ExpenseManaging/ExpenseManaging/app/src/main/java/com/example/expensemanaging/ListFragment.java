package com.example.expensemanaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListFragment extends Fragment {
    private SqliteHelper mDatabase;
    public RecyclerView expenseView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        expenseView = (RecyclerView)view.findViewById(R.id.expenses_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        expenseView.setLayoutManager(linearLayoutManager);
        expenseView.setHasFixedSize(true);

        mDatabase = new SqliteHelper((MainActivity) this.getContext());
        List<Expense> Expenses = mDatabase.listExpenses();

        if(Expenses.size() > 0){
            expenseView.setVisibility(View.VISIBLE);
            ExpenseAdapter mAdapter = new ExpenseAdapter(this.getContext(), Expenses);
            expenseView.setAdapter(mAdapter);

        }else {
            expenseView.setVisibility(View.GONE);
        }

    }
}