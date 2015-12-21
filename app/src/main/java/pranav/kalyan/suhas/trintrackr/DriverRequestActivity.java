package pranav.kalyan.suhas.trintrackr;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DriverRequestActivity extends AsyncTask<String, Void, String> {

    private Context context;

    public DriverRequestActivity(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0){

        String link;
        String username = arg0[0];
        String driverStatus = arg0[1];
        String driverLat = arg0[2];
        String driverLng = arg0[3];
        String data;
        BufferedReader bufferedReader;
        String result;

        data = "?user="+username;
        data += "&driverStatus="+driverStatus;
        data += "&lat=" + driverLat;
        data += "&lng=" + driverLng;

        try {
            link = "http://157.252.190.180/trintrackr/changeDriverStatus.php"+data;
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
                String message = "S Running:"+ jsonObj.getString("Active")+" Lat:"+jsonObj.getString("lat")+" Lng:"+jsonObj.getString("lng");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }
}
