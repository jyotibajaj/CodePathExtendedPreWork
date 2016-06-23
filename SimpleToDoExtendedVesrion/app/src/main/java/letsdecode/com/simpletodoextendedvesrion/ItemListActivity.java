package letsdecode.com.simpletodoextendedvesrion;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ItemListActivity extends AppCompatActivity implements ToDoListFragment.OnFragmentInteractionListener, AddItemFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Set Action bar icon and show that
        getSupportActionBar().setIcon(R.drawable.check);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle("Edit Task");



        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ToDoListFragment toDoFragment = new ToDoListFragment();
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.fragment_container, toDoFragment);
            fragmentTransaction.commit();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
