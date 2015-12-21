package pranav.kalyan.suhas.trintrackr;
//Hello



import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StudentRequestActivity extends AsyncTask<String,Void,String> {

    private Context context;
    private String retUsername;

    public String getRetUsername() {
        return retUsername;
    }

    public void setRetUsername(String retUsername) {
        this.retUsername = retUsername;
    }

    public int getRetStdStatus() {
        return retStdStatus;
    }

    public void setRetStdStatus(int retStdStatus) {
        this.retStdStatus = retStdStatus;
    }

    public int getRetLat() {
        return retLat;
    }

    public void setRetLat(int retLat) {
        this.retLat = retLat;
    }

    public int getRetLng() {
        return retLng;
    }

    public void setRetLng(int retLng) {
        this.retLng = retLng;
    }

    private int retStdStatus;
    private int retLat;
    private int retLng;


    public StudentRequestActivity(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0){

        String link;
        String username = arg0[0];
        String studentStatus = arg0[1];
        String driverLat = arg0[2];
        String driverLng = arg0[3];
        String data;
        BufferedReader bufferedReader;
        String result;

        data = "?user="+username;
        data += "&studentStatus="+studentStatus;
        data += "&lat=" + driverLat;
        data += "&lng=" + driverLng;

        try {
            link = "http://157.252.190.180/trintrackr/changeStudentStatus.php"+data;
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = bufferedReader.readLine();

        } catch (Exception e){
            return new String("Exception: " + e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){

        if (result != null) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                String message = "Requested:"+ jsonObj.getString("Active")+" Lat:"+jsonObj.getString("lat")+" Lng:"+jsonObj.getString("lng");

                /*NOT REQUIRED NOW - USED FOR TESTING */
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }

}
