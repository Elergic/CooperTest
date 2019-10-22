package com.example.coopertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.coopertest.Needs;

import java.util.List;

public class NeedsAdapter extends RecyclerView.Adapter<NeedsAdapter.ViewHolder> {
    private Context mcontext;
    private List<Needs> mNeedsList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView needsTitle,needsTime,needsPlace,needsOr_name,needsRes_name;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            needsTitle=(TextView)view.findViewById(R.id.news_title);
            needsTime=(TextView)view.findViewById(R.id.news_time);
            needsPlace=(TextView)view.findViewById(R.id.news_place);
            needsOr_name=(TextView)view.findViewById(R.id.news_or_name);
            needsRes_name=(TextView)view.findViewById(R.id.news_res_name);
        }
    }

    public NeedsAdapter(List<Needs> needsList) {
        mNeedsList=needsList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.news_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Needs needs = mNeedsList.get(position);
                Intent intent = new Intent(mcontext,CoopActivity.class);
                intent.putExtra(CoopActivity.COOP_TITLE,needs.getTitle());
                mcontext.startActivity(intent);
            }
        });
        return holder;
    }
    public void onBindViewHolder(ViewHolder holder,int position){
        Needs needs=mNeedsList.get(position);
        holder.needsTitle.setText(needs.getTitle());
        holder.needsTime.setText(needs.getTime());
        holder.needsPlace.setText(needs.getPlace());
        holder.needsOr_name.setText(needs.getOr_name());
        holder.needsRes_name.setText(needs.getRes_name());
    }
    public int getItemCount(){
        return mNeedsList.size();
    }
}