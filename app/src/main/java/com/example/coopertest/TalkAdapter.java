package com.example.coopertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.ViewHolder> {
    private Context mcontext;
    private List<Talk> mTalkList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView talkTitle,talkTime,talkName;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            talkTitle=(TextView)view.findViewById(R.id.title);
            talkTime=(TextView)view.findViewById(R.id.time);
            talkName=(TextView)view.findViewById(R.id.name);
        }
    }

    public TalkAdapter(List<Talk> TalkList) {
        mTalkList=TalkList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Talk talk = mTalkList.get(position);
                Intent intent = new Intent(mcontext,TalkInfoActivity.class);
                intent.putExtra(TalkInfoActivity.TALK_TITLE,talk.getTitle());
                mcontext.startActivity(intent);
            }
        });
        return holder;
    }
    public void onBindViewHolder(ViewHolder holder,int position){
        Talk talk=mTalkList.get(position);
        holder.talkTitle.setText(talk.getTitle());
        holder.talkTime.setText(talk.getTime());
        holder.talkName.setText(talk.getName());
    }
    public int getItemCount(){
        return mTalkList.size();
    }
}