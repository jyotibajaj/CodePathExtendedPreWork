package letsdecode.com.simpletodoextendedvesrion;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NotPurchasedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotPurchasedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = NotPurchasedFragment.class.getSimpleName();
    public static final String ITEM = "SELECTED_ITEM_VALUE";
    private OnFragmentInteractionListener mListener;

    private List<String> items = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> itemsAdapter;
    private Button addButton;


    //private OnFragmentInteractionListener mListener;

    public NotPurchasedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotPurchasedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotPurchasedFragment newInstance(String param1, String param2) {
        NotPurchasedFragment fragment = new NotPurchasedFragment();
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
        // initializing list view
        listView = (ListView) view.findViewById(R.id.listView_notPurchased);
        SQLiteDataAdapter sqLiteDataAdapterNotPurchased = new SQLiteDataAdapter(getActivity().getApplicationContext());
        // data model
         ArrayList<Item> itemList = sqLiteDataAdapterNotPurchased.getToDoItemData();
        ArrayList<ListViewItem> listItems = new ArrayList<>();
        listItems.add(new ListViewItem(TypeClass.TIME_VIEW, new Time(new Date())));
        for (Item i: itemList) {
            listItems.add(new ListViewItem(TypeClass.ITEM_DETAIL_VIEW, i));
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), R.layout.listview_layout,listItems);
        listView.setAdapter(listViewAdapter);
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

