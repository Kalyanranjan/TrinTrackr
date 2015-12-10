package pranav.kalyan.suhas.trintrackr;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeScreenActivity extends AppCompatActivity {

    private Button mLoginAsDriverButton;
    private Button mLoginAsStudentButton;

    private Button mSubmitDriverLoginButton;
    private Button mSubmitStudentLoginButton;

    private Button mCancelLoginDriverButton;
    private Button mCancelLoginStudentButton;

    private TextView mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome_screen);

        // Going from login choose buttons to respective login forms
        mLoginAsDriverButton = (Button) findViewById(R.id.driverLoginButton);
        mLoginAsDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginSelect).setVisibility(LinearLayout.GONE);
                findViewById(R.id.loginDriver).setVisibility(LinearLayout.VISIBLE);
            }
        });

        mLoginAsStudentButton = (Button) findViewById(R.id.studentLoginButton);
        mLoginAsStudentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginSelect).setVisibility(LinearLayout.GONE);
                findViewById(R.id.loginStudent).setVisibility(LinearLayout.VISIBLE);
            }
        });

        // Going back from login forms to login choose buttons
        mCancelLoginDriverButton = (Button) findViewById(R.id.cancelLoginDriverButton);
        mCancelLoginDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginDriver).setVisibility(LinearLayout.GONE);
                findViewById(R.id.loginSelect).setVisibility(LinearLayout.VISIBLE);
                ((TextView) findViewById(R.id.driverLoginMessage)).setText("");
                ((TextView) findViewById(R.id.studentLoginMessage)).setText("");
            }
        });

        mCancelLoginStudentButton = (Button) findViewById(R.id.cancelLoginStudentButton);
        mCancelLoginStudentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginStudent).setVisibility(LinearLayout.GONE);
                findViewById(R.id.loginSelect).setVisibility(LinearLayout.VISIBLE);
                ((TextView) findViewById(R.id.studentLoginMessage)).setText("");
                ((TextView) findViewById(R.id.studentLoginMessage)).setText("");
            }
        });

        // Going inside after login
        mSubmitStudentLoginButton = (Button) findViewById(R.id.submitLoginStudentButton);
        mSubmitStudentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String suser = ((EditText) findViewById(R.id.student_username)).getText().toString();
                String spass = ((EditText) findViewById(R.id.student_passcode)).getText().toString();

                if (suser.equals("") && spass.equals("")) {
                    ((TextView) findViewById(R.id.studentLoginMessage)).setText("");
                    Intent i = new Intent(WelcomeScreenActivity.this, StudentMapActivity.class);
                    startActivity(i);
                } else {
                    ((TextView) findViewById(R.id.studentLoginMessage)).setText("Invalid Credentials!");
                }
            }
        });

        mSubmitDriverLoginButton = (Button) findViewById(R.id.submitLoginDriverButton);
        mSubmitDriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String duser = ((EditText) findViewById(R.id.driver_username)).getText().toString();
                String dpass = ((EditText) findViewById(R.id.driver_passcode)).getText().toString();

                if (duser.equals("") && dpass.equals("")) {
                    ((TextView) findViewById(R.id.driverLoginMessage)).setText("");
                    Intent i = new Intent(WelcomeScreenActivity.this, DriverTracker.class);
                    startActivity(i);
                } else {
                    ((TextView) findViewById(R.id.driverLoginMessage)).setText("Invalid Credentials!");
                }
            }
        });

        mSignup = ((TextView) findViewById(R.id.signupText));
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeScreenActivity.this, DriverTracker.class);
                startActivity(i);
            }
        });
    }
}
