package along.yossi.longclick;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity{
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      tv=  (TextView) findViewById(R.id.textView);

        tv.setText("Hello!");


        (findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 MyLongTaskHelper longTaskHelper= new MyLongTaskHelper();
              //longTaskHelper.execute("Yonit Levi", "Dany Kushmaru", "Roni Daniel");
            try {
                int x = 0;
                int y = 10 / x;
            }catch (Exception e){
                Log.e("TAG",e.getMessage(), e);
            }


            }
        });
    }


    public class MyLongTaskHelper extends AsyncTask< String, String , String >
    {
        @Override
        protected void onPreExecute() {

            tv.setText("started");
        }

        @Override
        protected String doInBackground(String... params) {

            String allLines="";

            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;
            //http://www.recipepuppy.com/api?i=tomato
            try {
                url = new URL("http://api.fixer.io/latest");
                is = url.openStream();  // throws an IOException
                br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) != null) {

                    allLines=allLines+line;
                }
            } catch (MalformedURLException mue) {
                mue.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException ioe) {
                    // nothing to see here
                }
            }

            return allLines;
        }



        @Override
        protected void onPostExecute(String jsonText) {

            try {
                JSONObject mainObject= new JSONObject(jsonText);
                String dateFromJson= mainObject.getString("date");

                String baseCurrency= mainObject.getString("base");

                Toast.makeText(MainActivity.this, baseCurrency, Toast.LENGTH_SHORT).show();


               JSONObject innerObject = mainObject.getJSONObject("rates");

               double ils=   innerObject.getDouble("ILS");

                tv.setText("Current ILS rate For 1 Euro is: "+ils);



            } catch (JSONException e) {
                e.printStackTrace();
            }


           //
        }
    }



















}
