package letsdecode.com.simpletodoextendedvesrion;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {
    private Button addButton, lowButton, mediumButton, highButton, urgentButton;
    public static final String EDIT_KEY = "edit";
    public static final String ITEM_ID = "ID";
    private EditText taskNameEdit, dueDateEdit;
    SQLiteDataAdapter sqLiteDataAdapter;
    private boolean editMode = false;
    private String id = null;
    String priority = "";


    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String id, boolean edit) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        if (edit) {
            if (id == null && id.trim().isEmpty()) {
                new IllegalArgumentException("Id not provide for edit mode ");
            }
            args.putString(ITEM_ID, id);
            args.putBoolean(EDIT_KEY, Boolean.TRUE);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteDataAdapter = new SQLiteDataAdapter(getActivity().getApplicationContext());
        Bundle bundle = getArguments();
        editMode = (Boolean) bundle.get(EDIT_KEY);
        if (editMode != false) {
            //edit mode
            id = bundle.getString(ITEM_ID);
            Item item = sqLiteDataAdapter.getItemByID(id);
            initPageWith (item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_item, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    //taking reference of buttons and edit texts.
    //onclick event of add button
       /*
       getting text from edit texts and inserting into data base.
        */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //button references
        lowButton = (Button) view.findViewById(R.id.button_low);
        mediumButton = (Button) view.findViewById(R.id.button_med);
        highButton = (Button) view.findViewById(R.id.button_high);
        urgentButton = (Button) view.findViewById(R.id.button_urgent);

        addButton = (Button) view.findViewById(R.id.button_addItem);

        //click listeners
        lowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = lowButton.getText().toString();


            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = mediumButton.getText().toString();

            }
        });

        highButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = highButton.getText().toString();

            }
        });

        urgentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = urgentButton.getText().toString();

            }
        });


        //edit texts reference
        taskNameEdit = (EditText) view.findViewById(R.id.editText_taskName);
        dueDateEdit = (EditText) view.findViewById(R.id.editText_dueDate);
//        if (editMode) {
//            addButton.setText("Update");
//        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (editMode) {
//
////                    sqLiteDataAdapter.updateItemData();
//                } else {
                    String itemNameFromEdit = taskNameEdit.getText().toString();
                    Date date = new Date();
//                if (dueDateFromEdit != null) {
//                    dueDateInteger = Integer.parseInt(dueDateFromEdit);
//                }
                    if (itemNameFromEdit.isEmpty()) {
                        LogMessage.logInfo(getActivity().getApplicationContext(), "fields are empty");

                    } else {
                        sqLiteDataAdapter.insertItemData(itemNameFromEdit, date, priority);
                        FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.fragment_container, new NotPurchasedFragment()).commit();
                    }
                }

//            }
        });


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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
