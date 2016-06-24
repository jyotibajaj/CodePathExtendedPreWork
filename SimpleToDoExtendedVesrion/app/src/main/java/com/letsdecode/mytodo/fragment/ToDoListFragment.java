package com.letsdecode.mytodo.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.letsdecode.mytodo.adapters.ListViewAdapter;
import com.letsdecode.mytodo.adapters.SQLiteDataAdapter;
import com.letsdecode.mytodo.models.ListViewItem;
import com.letsdecode.mytodo.models.TaskDetail;
import com.letsdecode.mytodo.utils.ItemsBucketing;

import java.util.ArrayList;

import letsdecode.com.simpletodoextendedvesrion.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment implements ListViewAdapter.ItemClick {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = ToDoListFragment.class.getSimpleName();
    public static final String ITEM = "SELECTED_ITEM_VALUE";
    private OnFragmentInteractionListener mListener;
    private int savedPositionOfEditedItem;
    private int item_id = 0;
    //private Button doneButton;
    private ImageButton addItemImageButton;
    private CardView mCardView;


    //recycler view
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    //    private List<String> items = new ArrayList<>();

    ArrayList<ListViewItem> listItems = new ArrayList<>();


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
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("SimpleToDoExtendedVersion");


    }

    @Override
    public void onPause() {
        super.onPause();
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
        addItemImageButton = (ImageButton) view.findViewById(R.id.imageButton_add);

        addItemImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container, AddItemFragment.newInstance(0, false)).addToBackStack(null).commit();

            }
        });


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)


        SQLiteDataAdapter sqLiteDataAdapterNotPurchased = new SQLiteDataAdapter(getActivity().getApplicationContext());
        // data model
        ArrayList<TaskDetail> itemList = sqLiteDataAdapterNotPurchased.getToDoItemData();
        ItemsBucketing itemsBucketing = new ItemsBucketing();
        listItems = itemsBucketing.createBuckets(itemList);


//        listView.setAdapter(listViewAdapter);
//        listViewAdapter.notifyDataSetChanged();

        mAdapter = new ListViewAdapter(this,listItems);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {


            }


            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


//


//        //setting onClickListener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ListViewItem listViewItem = listItems.get(position);
//                // check if type is time or item
//                int type = listViewItem.getType();
//                if(type == TypeClass.ITEM_DETAIL_VIEW){
//                    //if type is item type, retrieve its id
//                    TaskDetail itemType = (TaskDetail)listViewItem.getObject();
//                    item_id = itemType.getId();
//                }
//                savedPositionOfEditedItem = position;
//                Log.d(TAG, "setupListViewShortListener. onItemClick: @@@@@@@" + position);
//                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
//                fragmentTransaction3.replace(R.id.fragment_container, AddItemFragment.newInstance(item_id, true))
//                        .addToBackStack(null).commit();
//
//            }
//        });


        });


    }

    @Override
    public void onItemClicked(TaskDetail itemType) {
        // check if type is time or item
        item_id = itemType.getId();
//        savedPositionOfEditedItem = e.getActionIndex();
//        Log.d(TAG, "setupListViewShortListener. onItemClick: @@@@@@@" + e.getActionIndex());
        FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.fragment_container, AddItemFragment.newInstance(item_id, true))
                .addToBackStack(null).commit();
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

