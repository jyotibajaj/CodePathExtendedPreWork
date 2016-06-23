package letsdecode.com.simpletodoextendedvesrion;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aashi on 15/06/16.
 */
public class ListViewAdapter extends ArrayAdapter<ListViewItem> {
    private Context context;

    Activity activity;
    private ArrayList<ListViewItem> listItems;


    public ListViewAdapter(Activity activity, int listview_layout, ArrayList<ListViewItem> listItems) {
        super(activity, R.layout.listview_layout);
        this.activity = activity;
        this.listItems = listItems;
    }

    //Since we have two types of items here, we'll return 2.
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }

    @Override
    public void add(ListViewItem itemJava) {
        super.add(itemJava);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }


    @Override
    public ListViewItem getItem(int position) {
        return super.getItem(position);
    }
//    @Override
//    public int getItemViewType (int position){
//        ListViewItem listItem = listItems
//                .get(position);
//        return listItem.getType();
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater itemInflater = LayoutInflater.from(getContext());

        //Let's assume the two layouts to inflated are called list_item_layout and header_layout.
        View view = itemInflater.inflate(
                R.layout.listview_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.textView_item);

        ListViewItem listItem = listItems
                .get(position);
        switch (listItem.getType()) {
            case TypeClass.TIME_VIEW:
                Time timeObject = (Time) listItem.getObject();

                textView.setText("Today");
                break;
            case TypeClass.ITEM_DETAIL_VIEW:
                Item itemObject = (Item) listItem.getObject();
                textView.setText(itemObject.getItemName() + ":" + itemObject.getStatus() +  ":" +
                        itemObject.getPriority() + ":" + itemObject.getTime());
                break;
        }
        return view;
    }


}

