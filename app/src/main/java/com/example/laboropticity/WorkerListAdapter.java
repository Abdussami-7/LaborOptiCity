package com.example.laboropticity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkerListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> nameList;
    ArrayList<String> skillList;
    ArrayList<String> locationsList;
    ArrayList<String> uidsList;
    LayoutInflater inflater;
    public WorkerListAdapter(Context ctx, ArrayList<String> wNames, ArrayList<String> wSkills, ArrayList<String> wLocations, ArrayList<String> wUids){
        this.context = ctx;
        this.nameList = wNames;
        this.skillList = wSkills;
        this.locationsList = wLocations;
        this.uidsList = wUids;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return nameList.toArray().length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.workers_list_item, null);
        TextView tUid = (TextView) view.findViewById(R.id.wUids);
        TextView tName = (TextView) view.findViewById(R.id.wNames);
        TextView tSkill = (TextView) view.findViewById(R.id.wSkills);
        TextView tLocation = (TextView) view.findViewById(R.id.wLocations);
        tUid.setText(uidsList.get(i));
        tName.setText(tName.getText()+nameList.get(i));
        tSkill.setText(tSkill.getText()+skillList.get(i));
        tLocation.setText(tLocation.getText()+locationsList.get(i));
        return view;
    }
}
