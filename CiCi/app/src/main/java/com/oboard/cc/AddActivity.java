package com.oboard.cc;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.arlib.floatingsearchview.FloatingSearchView;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        
        FloatingSearchView fsv;
        fsv = (FloatingSearchView)findViewById(R.id.add_fsv);
        fsv.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() { 
                public void onHomeClicked() {
                    finish();
                }
        });
    }
    
}
