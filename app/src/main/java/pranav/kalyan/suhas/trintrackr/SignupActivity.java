package pranav.kalyan.suhas.trintrackr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {

    private Button mSignedup;
    private Button mCancelSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mSignedup = (Button) findViewById(R.id.submitSignupDriverButton);
        mSignedup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWelcomeFromSignup();
            }
        });

        mCancelSignup = (Button) findViewById(R.id.cancelSignupDriverButton);
        mCancelSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWelcomeFromSignup();
            }
        });




    }

     private void goToWelcomeFromSignup() {
         Intent i = new Intent(SignupActivity.this, WelcomeScreenActivity.class);
        startActivity(i);
    }



}
