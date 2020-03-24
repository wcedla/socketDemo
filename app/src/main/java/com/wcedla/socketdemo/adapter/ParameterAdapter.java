package com.wcedla.socketdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wcedla.socketdemo.R;
import com.wcedla.socketdemo.data.ParameterData;

import java.util.ArrayList;
import java.util.List;

public class ParameterAdapter extends RecyclerView.Adapter<ParameterAdapter.ParameterViewHolder> {

    Context context;
    List<ParameterData> dataList;
    List<Integer> selectList=new ArrayList<>();

    public ParameterAdapter(Context context, List<ParameterData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ParameterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_parameter_item,parent,false);
        ParameterViewHolder parameterViewHolder=new ParameterViewHolder(view);
        return parameterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParameterViewHolder holder, final int position) {
        holder.parameter.setText(dataList.get(position).getParameterName());
        holder.parameter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectList.add(position);
                }else {
                    selectList.remove(Integer.valueOf(position));
                }
            }
        });
    }

    public List<Integer> getSelect()
    {
        return selectList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ParameterViewHolder extends RecyclerView.ViewHolder{

        CheckBox parameter;

        public ParameterViewHolder(@NonNull View itemView) {
            super(itemView);
            parameter=itemView.findViewById(R.id.parameterItem);
        }
    }

}
