package com.example.asynctaskrealizeprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView tv_one;
    private ImageView img_one,img_two;
    private ProgressBar progressBar;
    private ProgressTask progressTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_one = findViewById(R.id.tv_one);
        img_one = findViewById(R.id.img_start);
        img_two = findViewById(R.id.img_pause);
        progressBar = findViewById(R.id.pb_one);
        progressTask = new ProgressTask();

        img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressTask.isCancelled()){
                    progressTask = new ProgressTask();
                }
                progressTask.execute();
            }
        });
        img_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressTask.cancel(true);
            }
        });
    }
    class ProgressTask extends AsyncTask<Void,Integer,String>{

        //执行线程任务之前的操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //接受输入的参数，执行任务中的耗时操作。返回线程任务的执行结果
        @Override
        protected String doInBackground(Void... voids) {

            try {
                for(int i = 0; i<=100;i++){
                    publishProgress(i);
                    Thread.sleep(50);
                }
                return "加载完毕";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        //在主线程中显示线程任务的执行进度，在doInBackground方法中调用publishProgress方法则触发该方法
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            tv_one.setText("加载"+values[0]+"%");
        }

        //接受线程任务的执行结果，执行结果显示在界面上
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null){
                tv_one.setText(s);
            }
            progressTask = new ProgressTask();
        }

        //取消异步任务时触发该方法
        @Override
        protected void onCancelled() {
            super.onCancelled();
            tv_one.setText("已取消");
            progressBar.setProgress(0);

        }
    }
}