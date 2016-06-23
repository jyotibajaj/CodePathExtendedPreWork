package letsdecode.com.simpletodoextendedvesrion;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class ItemListActivity extends AppCompatActivity implements ToDoListFragment.OnFragmentInteractionListener, AddItemFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Set Action bar icon and show that
        getSupportActionBar().setIcon(R.drawable.check);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ToDoListFragment toDoFragment = new ToDoListFragment();
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.fragment_container, toDoFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask((Activity) getApplicationContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
