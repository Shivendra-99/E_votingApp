package com.example.evotingapp.Adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.evotingapp.R;
import com.example.evotingapp.model.SetData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AutoCompleteAdopter extends ArrayAdapter<SetData> {
   List<SetData> list;
    public AutoCompleteAdopter(@NonNull Context context, @NonNull List<SetData> list) {
        super(context, 0, list);
        list=new ArrayList<>(list);
    }
    public SetData getPosition(@Nullable int position) {
        return list.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.candidate_list,parent,false);

        }
        TextView textView=(TextView)convertView.findViewById(R.id.name);
        SetData setData=getItem(position);
        if(setData!=null) {
            textView.setText(setData.getName());
        }
        return convertView;
    }
}
