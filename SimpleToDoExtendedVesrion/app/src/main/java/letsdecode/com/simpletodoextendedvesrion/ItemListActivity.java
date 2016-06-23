package letsdecode.com.simpletodoextendedvesrion;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ItemListActivity extends AppCompatActivity implements ToDoListFragment.OnFragmentInteractionListener, DoneFragment.OnFragmentInteractionListener, AddItemFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Set Action bar icon and show that
        getSupportActionBar().setIcon(R.drawable.check);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final ToDoListFragment toDoFragment = new ToDoListFragment();
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.fragment_container, toDoFragment);
            fragmentTransaction.commit();
        }





//        doneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
//                fragmentTransaction2.replace(R.id.fragment_container, new DoneFragment()).commit();
//
//            }
//        });

//        addItemButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
//                fragmentTransaction3.replace(R.id.fragment_container, AddItemFragment.newInstance(0, false)).commit();
//
//            }
//        });
    }

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
