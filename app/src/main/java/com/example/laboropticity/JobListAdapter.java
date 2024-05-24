package com.example.laboropticity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class JobListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> titleList;
    ArrayList<String> descList;
    ArrayList<String> skillList;
    ArrayList<String> cityList;
    ArrayList<String> amountList;
    ArrayList<String> uUidList;
    ArrayList<String> jPReqList;
    LayoutInflater inflater;

    public JobListAdapter(Context context, ArrayList<String> titleList, ArrayList<String> descList, ArrayList<String> skillList, ArrayList<String> cityList, ArrayList<String> amountList, ArrayList<String> uUidList, ArrayList<String> jPReqList) {
        this.context = context;
        this.titleList = titleList;
        this.descList = descList;
        this.skillList = skillList;
        this.cityList = cityList;
        this.amountList = amountList;
        this.uUidList = uUidList;
        this.jPReqList = jPReqList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titleList.toArray().length;
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

        view = inflater.inflate(R.layout.job_list_item, null);

        TextView titleTV = view.findViewById(R.id.jTitleTV);
        TextView descTV = view.findViewById(R.id.jDescTV);
        TextView skillTV = view.findViewById(R.id.jSkillTV);
        TextView cityTV = view.findViewById(R.id.jCityTV);
        TextView amountTV = view.findViewById(R.id.jAmountTV);
        TextView uUidTV = view.findViewById(R.id.jUUidTV);
        TextView jPReqIdTV = view.findViewById(R.id.jPReqIdTV);
//        Button acceptBTN = view.findViewById(R.id.jAcceptBtn);

        titleTV.setText(titleTV.getText()+titleList.get(i));
        descTV.setText(descTV.getText()+descList.get(i));
        skillTV.setText(skillTV.getText()+skillList.get(i));
        cityTV.setText(cityTV.getText()+cityList.get(i));
        amountTV.setText(amountTV.getText()+amountList.get(i));
        uUidTV.setText(uUidList.get(i));
        jPReqIdTV.setText(jPReqList.get(i));
//        acceptBTN.setText("Accept");

        return view;
    }
}
