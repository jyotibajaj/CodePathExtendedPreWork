package com.letsdecode.mytodo.adapters;

import android.content.Context;
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
    private Context context;
    ItemClick itemClick;

    public interface ItemClick {
        void onItemClicked(TaskDetail t);
    }


    private ArrayList<ListViewItem> listItems;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView itemNameTextView, itemPriority;
        public CardView cardView;
        public TextView time_view;


        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            time_view = (TextView) itemView.findViewById(R.id.time_view);
            itemNameTextView = (TextView) itemView.findViewById(R.id.textView_itemName);
            itemPriority = (TextView) itemView.findViewById(R.id.textView_itemPriority);
        }
    }


    public ListViewAdapter(ItemClick itemClick, ArrayList<ListViewItem> listItems) {
//        this.context = context;
        this.itemClick = itemClick;
        this.listItems = listItems;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_layout, parent, false);
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
        //holder.textView = (TextView)findViewById(R.id.textView_item);
        ListViewItem listItem = listItems
                .get(position);
        switch (listItem.getType()) {
            case TypeClass.TIME_VIEW:

                String time = (String) listItem.getObject();
                holder.time_view.setVisibility(View.VISIBLE);
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


}