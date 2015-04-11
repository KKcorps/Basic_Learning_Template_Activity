package com.kkcorps.bmltoolkitandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kkcorps.bmltoolkitandroid.BasicLearningTemplate.BasicLearningItem;

import java.util.List;

/**
 * Created by root on 23/3/15.
 */
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder>{
    private List<BasicLearningItem> infoList;

    public InfoAdapter(List<BasicLearningItem> contactList) {
        this.infoList = contactList;
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    @Override
    public void onBindViewHolder(InfoHolder infoViewHolder, int i) {
        BasicLearningItem ci = infoList.get(i);
        InfoHolder.Info.setText(ci.getTitle());
        InfoHolder.InfoDescription.setText(ci.getDescription());
    }

    @Override
    public InfoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.basic_simulator_card, viewGroup, false);

        return new InfoHolder(itemView);
    }

    public static class InfoHolder extends RecyclerView.ViewHolder{
        protected static TextView Info,InfoDescription;
        public InfoHolder(View v){
            super(v);
            Info = (TextView) v.findViewById(R.id.cardTitleView);
            InfoDescription = (TextView) v.findViewById(R.id.cardDescription);
        }
    }
}
