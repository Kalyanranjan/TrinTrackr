package pranav.kalyan.suhas.trintrackr;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class WelcomeScreenActivity extends AppCompatActivity {

    private Button mLoginAsDriverButton;
    private Button mLoginAsStudentButton;

    private Button mSubmitDriverLoginButton;
    private Button mSubmitStudentLoginButton;

    private Button mCancelLoginDriverButton;
    private Button mCancelLoginStudentButton;

    private TextView mSignup;

    public static final String USER_NAME = "USER_NAME";

    public static final String PASSWORD = "PASSWORD";

    private static final String LOGIN_URL = "http://suhas.netau.net/login.php";

    private EditText editTextUserName;
    private EditText editTextPassword;

    private Button buttonLogin;

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

                login(suser, spass);

            }
        });

        mSubmitDriverLoginButton = (Button) findViewById(R.id.submitLoginDriverButton);
        mSubmitDriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String duser = ((EditText) findViewById(R.id.driver_username)).getText().toString();
                String dpass = ((EditText) findViewById(R.id.driver_passcode)).getText().toString();

                if (duser.equals("driver") && dpass.equals("driver")) {
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

                Intent i = new Intent(WelcomeScreenActivity.this, StudentSignupActivity.class);
             
                startActivity(i);
            }
        });

    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void login(String username, String password){
        userLogin(username,md5(password));
    }

    private void userLogin(final String username, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(WelcomeScreenActivity.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    ((TextView) findViewById(R.id.studentLoginMessage)).setText("");
                    Intent intent = new Intent(WelcomeScreenActivity.this,StudentMapActivity.class);
                    startActivity(intent);
                }else{
                    ((TextView) findViewById(R.id.studentLoginMessage)).setText("Invalid Credentials!");
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("username",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username,password);
    }
}
