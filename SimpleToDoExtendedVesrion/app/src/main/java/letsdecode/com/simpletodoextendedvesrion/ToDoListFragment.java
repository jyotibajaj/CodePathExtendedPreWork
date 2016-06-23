package letsdecode.com.simpletodoextendedvesrion;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = ToDoListFragment.class.getSimpleName();
    public static final String ITEM = "SELECTED_ITEM_VALUE";
    private OnFragmentInteractionListener mListener;
    private int savedPositionOfEditedItem;
    private int item_id = 0;
    //private Button doneButton;
    private ImageButton addItemImageButton;


    //    private List<String> items = new ArrayList<>();
    private ListView listView;
    ArrayList<ListViewItem> listItems = new ArrayList<>();
    private Button addButton;


    //private OnFragmentInteractionListener mListener;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoListFragment newInstance(String param1, String param2) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_not_purchased, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listItems.clear();
        //button reference
        addItemImageButton = (ImageButton)view.findViewById(R.id.imageButton_add);

        addItemImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container, AddItemFragment.newInstance(0, false)).addToBackStack(null).commit();

            }
        });
        // initializing list view
        listView = (ListView) view.findViewById(R.id.listView_notPurchased);
        SQLiteDataAdapter sqLiteDataAdapterNotPurchased = new SQLiteDataAdapter(getActivity().getApplicationContext());
        // data model
         ArrayList<Item> itemList = sqLiteDataAdapterNotPurchased.getToDoItemData();
        listItems.add(new ListViewItem(TypeClass.TIME_VIEW, new Time(new Date())));
        for (Item i: itemList) {
            listItems.add(new ListViewItem(TypeClass.ITEM_DETAIL_VIEW, i));
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), R.layout.listview_layout,listItems);
        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();

        //setting onClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListViewItem listViewItem = listItems.get(position);
                // check if type is time or item
                int type = listViewItem.getType();
                if(type == TypeClass.ITEM_DETAIL_VIEW){
                    //if type is item type, retrieve its id
                    Item itemType = (Item)listViewItem.getObject();
                    item_id = itemType.getId();
                }
                savedPositionOfEditedItem = position;
                Log.d(TAG, "setupListViewShortListener. onItemClick: @@@@@@@" + position);
                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container, AddItemFragment.newInstance(item_id, true))
                        .addToBackStack(null).commit();

            }
        });



    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

