package pranav.kalyan.suhas.trintrackr;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetStLocActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private String link;
    private BufferedReader bufferedReader;
    private String result;

    private int stNum = 0;
    private String[] students = new String[255]; //Only 85 drivers max

    public int getStNum() {
        return stNum;
    }

    private void setStNum(int stNum) {
        this.stNum = stNum;
    }

    public String[] getStudents() {
        return students;
    }

    private void setStudents(int i, String sName, String sLat, String sLng) {
        this.students[3*i-3] = sName;
        this.students[3*i-2] = sLat;
        this.students[3*i-1] = sLng;
    }

    public String toString(){
        String string = "|";
        for (int i=1; i<=stNum; i++){
            string+=this.students[3*i-3]+" | "+this.students[3*i-2]+" | "+this.students[3*i-1]+" | ";
        }
        return string;
    }

    public GetStLocActivity(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0){
        try {
            link = "http://157.252.190.180/trintrackr/getStudentLocation.php";
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
                JSONObject jsonObj2;
                String temp1="", temp2="", temp3="";
                this.setStNum(Integer.parseInt(jsonObj.getString("num")));
                for (int x=1; x <= this.stNum; x++) {
                    jsonObj2 = new JSONObject(jsonObj.getString(String.valueOf(x)));
                    temp1 = jsonObj2.getString(String.valueOf("username"));
                    temp2 = jsonObj2.getString(String.valueOf("lat"));
                    temp3 = jsonObj2.getString(String.valueOf("lng"));
                    this.setStudents(x, temp1, temp2, temp3);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }
    }

}
