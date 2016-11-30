package fr.wildcodeschool.wildcaching;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class HintActivity extends AppCompatActivity {

    private ImageView imageView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        TextView title = (TextView) findViewById(R.id.text1);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "TravelingTypewriter.ttf");
        title.setTypeface(typeface);

        TextView title2 = (TextView) findViewById(R.id.text2);
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "TravelingTypewriter.ttf");
        title2.setTypeface(typeface2);

        TextView title3 = (TextView) findViewById(R.id.textBottom);
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "TravelingTypewriter.ttf");
        title3.setTypeface(typeface3);

        TextView title4 = (TextView) findViewById(R.id.text3);
        Typeface typeface4 = Typeface.createFromAsset(getAssets(), "TravelingTypewriter.ttf");
        title4.setTypeface(typeface4);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.backApp2));
        }



        this.imageView = (ImageView) findViewById(R.id.hint_picture);
        this.url = getIntent().getStringExtra(Constants.IMG_URL);
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {
        private String TAG = "WCS - AsyncTask";
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            getImageFromUrl();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }

    }

    private void getImageFromUrl() {
        try {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                URL newurl = new URL(this.url);
                final Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(mIcon_val);
                    }
                });
            } else {
                Toast.makeText(this, "no connection", Toast.LENGTH_SHORT).show();
            }

        }
        catch (MalformedURLException e){
            Log.d("MalformedURLException", e.toString());
            Intent ficheIntent = new Intent(this,MainActivity.class);
            startActivity(ficheIntent);
            this.finish();
        }
        catch (IOException e){
            Log.d("IOException", e.toString());
            Intent ficheIntent = new Intent(this,MainActivity.class);
            startActivity(ficheIntent);
            this.finish();
        }
    }
}
