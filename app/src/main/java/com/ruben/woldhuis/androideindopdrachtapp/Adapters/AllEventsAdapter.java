package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Event;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Meetup;
import com.ruben.woldhuis.androideindopdrachtapp.R;
import com.ruben.woldhuis.androideindopdrachtapp.View.Activities.EventDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class AllEventsAdapter extends RecyclerView.Adapter<com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter.MyViewHolder> implements Serializable {

    Context mContext;
    Event meetup;
    private ArrayList<Event> dataSource;

    public AllEventsAdapter(Context context, ArrayList<Event> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter.MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull AllEventsAdapter.MyViewHolder myViewHolder, int i) {
        meetup = dataSource.get(i);
        myViewHolder.title.setText(meetup.getEventName());
    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view, final Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.event_name_TextView);


            //TODO: Event veraderen naar Meetup ff wachten op antwoord want welke is het?
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Event i = dataSource.get(getAdapterPosition());
                    Intent intent = new Intent(view.getContext(), EventDetailActivity.class);
                    intent.putExtra("Meetup", (Serializable) i);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }
}
