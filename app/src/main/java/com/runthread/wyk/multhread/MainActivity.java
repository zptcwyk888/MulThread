package com.runthread.wyk.multhread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mLoadImg, mToast;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static final String TAG = "ThreadName";

    private Handler mHandler = new Handler(){

        @Override

        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                  //Log.d(TAG, "handleMessage: "+"first handler");
                   mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mProgressBar.setProgress((Integer) msg.obj);
                    break;
                case 2:
                    mImageView.setImageBitmap((Bitmap) msg.obj);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

/*
    private Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            Log.d(TAG, "handleMessage: second handler");
            mProgressBar.setVisibility(View.VISIBLE);
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.activity_main_img_view);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        mLoadImg = (Button) findViewById(R.id.activity_main_load_img);
        mLoadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadimg();
               // new LoadImageTask().execute();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = mHandler.obtainMessage();
                        message.what = 0;
                        mHandler.sendMessage(message);
                        for (int i = 0; i < 11; i++) {
                            sleep();
                            Message message2 = mHandler.obtainMessage();
                            message2.what = 1;
                            message2.obj = i*10;
                            mHandler.sendMessage(message2);

                        }
                        Bitmap mBitMap = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_launcher);
                        Message message3 = mHandler.obtainMessage();
                        message3.what = 2;
                        message3.obj = mBitMap;
                        mHandler.sendMessage(message3);


                    }

                    private void sleep() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }


        });

        mToast = (Button) findViewById(R.id.activity_main_toast);
        mToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "show toast", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*private void loadimg()  {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap mBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(mBitMap);
                    }
                });
               // mImageView.setImageBitmap(mBitMap);

            }
        }).start();

    }*/
    /*class LoadImageTask extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
            for (int i = 0; i < 11; i++) {
                sleep();
                publishProgress(i * 10);
            }

            Bitmap mBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            return mBitMap;

        }

        private void sleep() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //super.onPostExecute(bitmap);
            Log.d(TAG, "onPostExecute: " + Thread.currentThread().getName());
            mImageView.setImageBitmap(bitmap);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }
    }*/
}
