package com.joelimyx.sqliteemploye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.macy_button) Button mMacyButton;
    @BindView(R.id.boston_button) Button mBostonButton;
    @BindView(R.id.salary_button) Button mSalaryButton;

    EmployeeHelper mEmployeeHelper;
    EmployeeAdapter mEmployeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mEmployeeHelper = EmployeeHelper.getInstance(this);
        mEmployeeHelper.populateData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mEmployeeAdapter = new EmployeeAdapter(new ArrayList<String>());
        mRecyclerView.setAdapter(mEmployeeAdapter);

        mMacyButton.setOnClickListener(this);
        mBostonButton.setOnClickListener(this);
        mSalaryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        List<String> list = new LinkedList<>();
        switch (view.getId()){
            case R.id.macy_button:
                list = mEmployeeHelper.getEmployeeAtMacy();
                mEmployeeAdapter.setList(list);
                break;
            case R.id.boston_button:
                list = mEmployeeHelper.getCompaniesAtBoston();
                mEmployeeAdapter.setList(list);
                break;
            case R.id.salary_button:
                list.add(mEmployeeHelper.getHighestSalary());
                mEmployeeAdapter.setList(list);
                break;
        }
    }
}
