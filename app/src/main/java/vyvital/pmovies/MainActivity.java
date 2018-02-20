package vyvital.pmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vyvital.pmovies.fragments.MovieFragA;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportActionBar().setTitle("Popular Movies");
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, MovieFragA.newInstance(), "frag")
                    .commit();
        } else {
            getSupportFragmentManager()
                    .findFragmentByTag("frag");
        }
    }


}
