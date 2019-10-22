package com.example.coopertest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CoopAdapter extends RecyclerView.Adapter<CoopAdapter.ViewHolder> {

    private Context mContext;

    private List<Coop> mCoopList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView coopTitle;
        TextView coopPublictime;
        TextView coopPlace;
        TextView coopOrganization;
        TextView coopContact;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            coopTitle = (TextView) view.findViewById(R.id.coop_title);
            coopPublictime = (TextView) view.findViewById(R.id.coop_publictime);
            coopPlace = (TextView) view.findViewById(R.id.coop_place);
            coopOrganization = (TextView) view.findViewById(R.id.coop_organization);
            coopContact = (TextView) view.findViewById(R.id.coop_contact);
        }
    }

    public CoopAdapter(List<Coop> coopList){
        mCoopList = coopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.coop_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Coop coop = mCoopList.get(position);
                Intent intent = new Intent(mContext,CoopActivity.class);
                intent.putExtra(CoopActivity.COOP_TITLE,coop.getTitle());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Coop coop = mCoopList.get(position);
        holder.coopTitle.setText(coop.getTitle());
        holder.coopPublictime.setText(coop.getTime());
        holder.coopPlace.setText(coop.getPlace());
        holder.coopOrganization.setText(coop.getOrganizationName());
        holder.coopContact.setText(coop.getContact());
    }

    @Override
    public int getItemCount() {
        return mCoopList.size();
    }
}
