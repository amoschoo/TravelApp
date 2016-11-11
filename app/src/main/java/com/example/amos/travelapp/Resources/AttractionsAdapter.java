package com.example.amos.travelapp.Resources;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amos.travelapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 31/10/15.
 */
public class AttractionsAdapter extends ArrayAdapter {
    DataHandler handler;
    List list = new ArrayList<>();
    public AttractionsAdapter(Context context, int resource) {
        super(context, resource);
    }


    static class DataHandler
    {
        ImageView Picture;
        TextView name;
        TextView rating;
    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row  = inflater.inflate(R.layout.row_layout,parent,false);
            handler=new DataHandler();
            handler.Picture=(ImageView)row.findViewById(R.id.picture);
            handler.name=(TextView)row.findViewById(R.id.attraction);
            handler.rating=(TextView)row.findViewById(R.id.rating);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler)row.getTag();
        }
        AttractionDataProvider dataProvider;
        dataProvider = (AttractionDataProvider)this.getItem(position);
        handler.Picture.setImageResource(dataProvider.getPicture_resource());
        handler.name.setText(dataProvider.getAttraction_name());
        handler.rating.setText(dataProvider.getRating());
        return row;
    }
}
