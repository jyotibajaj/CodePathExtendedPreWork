package com.letsdecode.mytodo.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import com.letsdecode.mytodo.adapters.SQLiteDataAdapter;
import java.util.Calendar;
import java.util.Date;

import com.letsdecode.mytodo.models.TaskDetail;
import letsdecode.com.simpletodoextendedvesrion.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {
    private RadioButton lowButton, mediumButton, highButton, urgentButton;
    private View trashButton, addButton;
    private static SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("EEE, d MMM yyyy");

    public static final String EDIT_KEY = "edit";
    public static final String ITEM_ID = "ID";
    private EditText taskNameEdit;
    private DatePicker datePicker;
    SQLiteDataAdapter sqLiteDataAdapter;
    private TextInputLayout textInputLayout;
    TextInputLayout date;
    private boolean editMode = false;
    private String idItem = null;
    String priority = "low";
    EditText editText_duedate;
    Calendar calendar;
    TaskDetail item = null;

    long itemTime = new Date().getTime();


    private OnFragmentInteractionListener mListener;

    public AddItemFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(int id, boolean edit) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();

        if (edit) {
            if (id == 0) {
                new IllegalArgumentException("Id not provide for edit mode ");
            }
            args.putInt(ITEM_ID, id);
            args.putBoolean(EDIT_KEY, Boolean.TRUE);
        } else {
            args.putBoolean(EDIT_KEY, Boolean.FALSE);
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
        if (editMode) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Task");
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Task");
        }
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initPageWith(TaskDetail item) {
        taskNameEdit.setText(item.getItemName());
        calendar = Calendar.getInstance();
        calendar.setTime(new Date(Long.valueOf(item.getTime())));
        editText_duedate.setText(simpleDateFormat.format(calendar.getTime()));
//
//        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), null);
        priority = item.getPriority();
    }

    private void setPriorityState(String priority) {
        if (priority.equalsIgnoreCase("low")) {
            lowButton.setChecked(true);
//            lowButton.setPressed(true);
        } else if (priority.equalsIgnoreCase("medium")) {
            mediumButton.setChecked(true);
        } else if (priority.equalsIgnoreCase("high")) {
            highButton.setChecked(true);
        } else {
            urgentButton.setChecked(true);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //button references
        lowButton = (RadioButton) view.findViewById(R.id.button_low);
        mediumButton = (RadioButton) view.findViewById(R.id.button_med);
        highButton = (RadioButton) view.findViewById(R.id.button_high);
        urgentButton = (RadioButton) view.findViewById(R.id.button_urgent);
        addButton = view.findViewById(R.id.button_addItem);
        trashButton = view.findViewById(R.id.button_trash);
        trashButton.setVisibility(View.INVISIBLE);
        //edit texts reference

        textInputLayout = (TextInputLayout) view.findViewById(R.id.textView_taskName);
        taskNameEdit = (EditText) view.findViewById(R.id.editText_taskName);
//        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        date = (TextInputLayout)view.findViewById(R.id.date);
        date.setOnClickListener(editDateClickListener);
        editText_duedate = (EditText) view.findViewById(R.id.editText_duedate);
        editText_duedate.setOnClickListener(editDateClickListener);

        if (editMode == false) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            editText_duedate.setText(simpleDateFormat.format(calendar.getTime()));
        }

        if (editMode != false) {
            //edit mode
            trashButton.setVisibility(View.VISIBLE);
            int idToDelete = getArguments().getInt(ITEM_ID, -1);
            if (idToDelete != -1) {
                idItem = "" + idToDelete;
                item = SQLiteDataAdapter.getItemByID(idItem);
                initPageWith(item);
                priority = item.getPriority();
                setPriorityState(priority);
            }
        } else {
            setPriorityState(priority);
        }

        //click listener for trash buttons
        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked
                                int id1 = Integer.parseInt(idItem);
                                SQLiteDataAdapter.deleteItemData(id1);
                                getActivity().onBackPressed();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


        lowButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reset();
                priority = "low";
                lowButton.setChecked(true);
                return true;
            }
        });

        mediumButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reset();
                priority = "medium";
                mediumButton.setChecked(true);
                return true;
            }
        });

        highButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reset();
                priority = "high";
                highButton.setChecked(true);
                return true;
            }
        });


        urgentButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reset();
                priority = "urgent";
                urgentButton.setChecked(true);
                return true;
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar == null) {
                    date.setError("Enter a valid date");
                    return;
                }
                long time = calendar.getTime().getTime();
                if (taskNameEdit.getText().toString().trim().isEmpty()) {
                    textInputLayout.setError("Please enter a task name");
                    return;
                }
                if (editMode) {
                    int id = item.getId();
                    String itemName = taskNameEdit.getText().toString();
                    SQLiteDataAdapter.updateItemData(id, itemName, "" + time, priority);
                    getActivity().onBackPressed();
                } else {
                    String itemNameFromEdit = taskNameEdit.getText().toString();
                    if (itemNameFromEdit.isEmpty()) {

                    } else {
                        SQLiteDataAdapter.insertItemData(itemNameFromEdit, "" + time, priority);
                        getActivity().onBackPressed();

                    }
                }
            }
        });

    }

    private void reset() {
//        lowButton.setPressed(false);
//        mediumButton.setPressed(false);
//        highButton.setPressed(false);
//        urgentButton.setPressed(false);
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
//            editText_duedate.setText(calendar.getTime().toString());
            editText_duedate.setText(simpleDateFormat.format(calendar.getTime()));
        }
    };

    View.OnClickListener editDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(AddItemFragment.this.getActivity(), myDateListener, year, month, day).show();
        }
    };


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
