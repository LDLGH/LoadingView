package com.ldl.loadingview.way2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ldl.loadingview.R;
import com.ldl.loadingview.way1.glide.ProgressModelLoader;

import java.util.ArrayList;
import java.util.List;

public class LoadingTwoActivity extends AppCompatActivity {

    private ImageView iv_normal;
    private CustomProgressBar progress;

    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_two);

        iv_normal = findViewById(R.id.iv_normal);
        progress = findViewById(R.id.progress);

        urls.add("https://upload-images.jianshu.io/upload_images/1496626-aee2674d3411b6da.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539866991221&di=9ca367b46fd9c3dc994b0b4659d75356&imgtype=0&src=http%3A%2F%2Fp0.qhmsg.com%2Ft01c45ccdfc7eced1e4.gif");
        urls.add("https://upload-images.jianshu.io/upload_images/1496626-aee2674d3411b6da.jpg");

        Glide.with(this).using(new ProgressModelLoader(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                progress.setVisibility(View.VISIBLE);
                Log.d("CircleProgress",ArithmeticUtils.div(msg.arg1,msg.arg2,2)+"");

                progress.updateUI(ArithmeticUtils.div(msg.arg1,msg.arg2,2)*100);
                if(ArithmeticUtils.div(msg.arg1,msg.arg2,2) >= 1){
                    progress.setVisibility(View.GONE);
                }
            }
        })).load(urls.get(0)).placeholder(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_normal);
    }
}
