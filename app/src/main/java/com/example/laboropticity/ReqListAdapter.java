package com.example.laboropticity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReqListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> reqTitleList;
    ArrayList<String> reqDescList;
    ArrayList<String> reqAmtList;
    ArrayList<String> uUidList;
    ArrayList<String> reqIdList;
    LayoutInflater inflater;

    public ReqListAdapter(Context ctx, ArrayList<String> reqTitleList, ArrayList<String> reqDescList, ArrayList<String> reqAmtList, ArrayList<String> uUidList, ArrayList<String> reqIdList) {
        this.context = ctx;
        this.reqTitleList = reqTitleList;
        this.reqDescList = reqDescList;
        this.reqAmtList = reqAmtList;
        this.uUidList = uUidList;
        this.reqIdList = reqIdList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return reqTitleList.toArray().length;
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
    public View getView(int i, View view, ViewGroup viewGroup){
        view = inflater.inflate(R.layout.req_list_item, null);
        TextView rTitle = (TextView) view.findViewById(R.id.jReqTitleTV);
        TextView rDesc = (TextView) view.findViewById(R.id.jReqDescTV);
        TextView rAmt = (TextView) view.findViewById(R.id.jReqAmountTV);
        TextView rUUid = (TextView) view.findViewById(R.id.jReqUUidTV);
        TextView rId = (TextView) view.findViewById(R.id.jReqIdTV);

        rTitle.setText(rTitle.getText()+reqTitleList.get(i));
        rDesc.setText(rDesc.getText()+reqDescList.get(i));
        rAmt.setText(rAmt.getText()+reqAmtList.get(i));
        rUUid.setText(uUidList.get(i));
        rId.setText(reqIdList.get(i));
        return view;
    }
}
