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

        data = "?user ="+username;
        data += "&driverStatus="+driverStatus;
        data += "&lat=" + driverLat;
        data += "&lng=" + driverLng;

        //Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

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
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

        if (result != null) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                String query_result = jsonObj.getString("query_result");
                Toast.makeText(context, query_result, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
