package pranav.kalyan.suhas.trintrackr;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class WelcomeScreenActivity extends AppCompatActivity {

    private Button mLoginAsDriverButton;
    private Button mLoginAsStudentButton;

    private Button mSubmitDriverLoginButton;
    private Button mSubmitStudentLoginButton;

    private Button mCancelLoginDriverButton;
    private Button mCancelLoginStudentButton;

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
        mCancelLoginStudentButton = (Button) findViewById(R.id.cancelLoginStudentButton);
        mCancelLoginDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginDriver).setVisibility(LinearLayout.GONE);
                findViewById(R.id.loginSelect).setVisibility(LinearLayout.VISIBLE);
            }
        });

        mCancelLoginStudentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginStudent).setVisibility(LinearLayout.GONE);
                findViewById(R.id.loginSelect).setVisibility(LinearLayout.VISIBLE);
            }
        });;

        // Going inside after login
        mLoginAsDriverButton = (Button) findViewById(R.id.submitLoginDriverButton);
        mLoginAsDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeScreenActivity.this, StudentMapActivity.class);
                startActivity(i);
            }
        });

    }
}
