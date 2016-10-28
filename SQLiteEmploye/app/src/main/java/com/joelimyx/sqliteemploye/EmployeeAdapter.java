package com.joelimyx.sqliteemploye;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joe on 10/28/16.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.CustomViewHolder> {

    List<String> mList;

    public EmployeeAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<String> list){
        mList = list;
        notifyDataSetChanged();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder{

        @BindView(android.R.id.text1) TextView mTextView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
