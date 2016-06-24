package com.letsdecode.mytodo.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letsdecode.mytodo.models.ListViewItem;
import com.letsdecode.mytodo.models.TaskDetail;
import com.letsdecode.mytodo.models.TypeClass;

import java.util.ArrayList;

import letsdecode.com.simpletodoextendedvesrion.R;


public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private ItemClick itemClick;
    private ArrayList<ListViewItem> listItems;


    public interface ItemClick {
        void onItemClicked(TaskDetail taskDetail);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNameTextView, itemPriority;
        public CardView cardView;
        public TextView time_view;


        public ViewHolder(View itemView) {
            super(itemView);
            //getting reference of views
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            time_view = (TextView) itemView.findViewById(R.id.time_view);
            itemNameTextView = (TextView) itemView.findViewById(R.id.textView_itemName);
            itemPriority = (TextView) itemView.findViewById(R.id.textView_itemPriority);
        }
    }

    public ListViewAdapter(ItemClick itemClick, ArrayList<ListViewItem> listItems) {
        this.itemClick = itemClick;
        this.listItems = listItems;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_layout, parent, false);
        // Return a new holder instance
        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = viewHolder.getAdapterPosition();
                ListViewItem listViewItem = listItems.get(pos);
                if (listViewItem.getType() == TypeClass.TIME_VIEW) {
                    return;
                }
                TaskDetail taskDetail = (TaskDetail) listViewItem.getObject();
                if (taskDetail != null) {
                    itemClick.onItemClicked(taskDetail);
                }
            }
        });

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ListViewItem listItem = listItems
                .get(position);
        switch (listItem.getType()) {
            case TypeClass.TIME_VIEW:
                String time = (String) listItem.getObject();
                holder.time_view.setVisibility(View.VISIBLE);
                //setting color corresponding to time text
                if (time.equalsIgnoreCase("overdue")) {
                    holder.time_view.setTextColor(Color.RED);

                } else if (time.equalsIgnoreCase("today")) {
                    holder.time_view.setTextColor(Color.BLUE);

                } else {
                    int colorCode = getColor(time);
                    holder.time_view.setTextColor(colorCode);
                }
                holder.time_view.setText(time);
                holder.cardView.setVisibility(View.GONE);
                break;
            case TypeClass.ITEM_DETAIL_VIEW:
                holder.time_view.setVisibility(View.GONE);
                holder.cardView.setVisibility(View.VISIBLE);
                TaskDetail itemObject = (TaskDetail) listItem.getObject();
                holder.itemNameTextView.setText(itemObject.getItemName());
                holder.itemPriority.setText(itemObject.getPriority());
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listItems.size();
    }


    //logic to find out the random color for time text.
    private int getHashCode(String inputString) {
        int hashCode = 0;
        for (int i = 0; i < inputString.length(); i++) {
            hashCode = inputString.charAt(i) + ((hashCode - 6) - hashCode);
        }
        return hashCode;

    }

    private int convertToHex(int hashCode) {
        int var = (hashCode * 0x00ef56200);
        return var;

    }

    private int getColor(String time) {
        int hashCode = getHashCode(time);
        return convertToHex(hashCode);
    }


}