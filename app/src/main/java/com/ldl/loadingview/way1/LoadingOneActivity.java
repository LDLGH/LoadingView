package com.ldl.loadingview.way1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jpeng.progress.CircleProgress;
import com.jpeng.progress.enums.CircleStyle;
import com.ldl.loadingview.R;
import com.ldl.loadingview.way1.glide.ProgressModelLoader;
import com.ldl.loadingview.way1.picasso.SquareUtils;

import java.util.ArrayList;
import java.util.List;

public class LoadingOneActivity extends AppCompatActivity {

    private ImageView iv_normal;
    private ImageView iv_gif;
    private ImageView iv_picasso;
    private List<String> urls = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_one);
        iv_normal = findViewById(R.id.iv_normal);
        iv_gif = findViewById(R.id.iv_gif);
        iv_picasso = findViewById(R.id.iv_picasso);

        urls.add("https://upload-images.jianshu.io/upload_images/1496626-aee2674d3411b6da.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539866991221&di=9ca367b46fd9c3dc994b0b4659d75356&imgtype=0&src=http%3A%2F%2Fp0.qhmsg.com%2Ft01c45ccdfc7eced1e4.gif");
        urls.add("https://upload-images.jianshu.io/upload_images/1496626-aee2674d3411b6da.jpg");

        final CircleProgress progress = generateProgress(iv_normal);
        progress.inject(iv_normal);
        Glide.with(this).using(new ProgressModelLoader(new Handler() {
            @Override
            public void handleMessage(Message msg) {

                Log.d("CircleProgress",msg.arg1+"    "+msg.arg2);
                progress.setLevel(msg.arg1);
                progress.setMaxValue(msg.arg2);
            }
        })).load(urls.get(0)).placeholder(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_normal);

        final CircleProgress progress2 = generateProgress(iv_gif);
        progress2.inject(iv_gif);
        Glide.with(this).using(new ProgressModelLoader(new Handler() {
            @Override
            public void handleMessage(Message msg) {


                Log.d("CircleProgress",msg.arg1+"    "+msg.arg2);
                progress2.setLevel(msg.arg1);
                progress2.setMaxValue(msg.arg2);
            }
        })).load(urls.get(1)).asGif().placeholder(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_gif);

        final CircleProgress progress3 = generateProgress();
        progress3.inject(iv_picasso);

        final Handler handler = new Handler();

        SquareUtils.getPicasso(this, new SquareUtils.ProgressListener() {

            @Override
            public void update(final long current, final long total) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progress3.setMaxValue(total);
                        progress3.setLevel((int) current);
                    }
                });

            }
        }).load(urls.get(2)).placeholder(R.drawable.loading).into(iv_picasso);

    }

    private CircleProgress generateProgress(ImageView image) {
        CircleProgress.Builder builder = new CircleProgress.Builder()
                .setTextColor(getResources().getColor(R.color.colorAccent))
                .setStyle(CircleStyle.FAN);
        return builder.build();
    }

    private CircleProgress generateProgress() {
        CircleProgress.Builder builder = new CircleProgress.Builder()
                .setTextColor(getResources().getColor(R.color.colorAccent));
        return builder.build();
    }
}
