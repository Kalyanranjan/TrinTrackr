package pranav.kalyan.suhas.trintrackr;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDrLocActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private String link;
    private BufferedReader bufferedReader;
    private String result;

    private int drNum = 0;
    private String[] drivers = new String[255]; //Only 85 drivers max

    public int getDrNum() {
        return drNum;
    }

    private void setDrNum(int drNum) {
        this.drNum = drNum;
    }

    public String[] getDrivers() {
        return drivers;
    }

    private void setStudents(int i, String dName, String dLat, String dLng) {
        this.drivers[3*i-3] = dName;
        this.drivers[3*i-2] = dLat;
        this.drivers[3*i-1] = dLng;
    }

    public String toString(){
        String string = "|";
        for (int i=1; i<=drNum; i++){
            string+=this.drivers[3*i-3]+" | "+this.drivers[3*i-2]+" | "+this.drivers[3*i-1]+" | ";
        }
        return string;
    }

    public GetDrLocActivity(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0){
        try {
            link = "http://157.252.190.180/trintrackr/getDriverLocation.php";
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
        //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

        if (result != null) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject jsonObj2;
                String temp1="", temp2="", temp3="";
                this.setDrNum(Integer.parseInt(jsonObj.getString("num")));
                for (int x=1; x <= this.drNum; x++) {
                    jsonObj2 = new JSONObject(jsonObj.getString(String.valueOf(x)));
                    temp1 = jsonObj2.getString(String.valueOf("username"));
                    temp2 = jsonObj2.getString(String.valueOf("lat"));
                    temp3 = jsonObj2.getString(String.valueOf("lng"));
                    this.setStudents(x, temp1, temp2, temp3);
                }

                //Toast.makeText(context, temp1, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }

}
