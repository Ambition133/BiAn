
package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView1;
  //  ImageView imageView;
    Button downloadButton;
    BiAn bian;
    EditText editText1;
    Button resumeButton;
    Button pauseButton;
    TextView textView2;
    Handler handler;
 ListView lv1;
    ArrayList<Bitmap> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // imageView = (ImageView) findViewById(R.id.imageView);
        lv1 = (ListView) findViewById(R.id.listView1);
   list = new ArrayList<>();
lv1.setStackFromBottom(true);
        handler = new Handler();
        textView1 = (TextView) findViewById(R.id.TextView1);
        downloadButton = (Button) findViewById(R.id.download);
        resumeButton = (Button) findViewById(R.id.pause);
        pauseButton = (Button) findViewById(R.id.resume);
        editText1 = (EditText) findViewById(R.id.EditText1);
       textView2= findViewById(R.id.textView);
        downloadButton.setOnClickListener(this);
        resumeButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        textView2.append("实时动态数据区:");
        textView1.append("彼岸桌面壁纸\n壁纸预览请打开网址http://www.netbian.com/\n壁纸的大小为1920*1080\n请选择你要下载的壁纸类型\t\n1.日历壁纸  2.动漫壁纸  3.风景壁纸  4.美女壁纸  5.游戏"
                + "\n6.影视        7.动态  8.唯美壁纸  9.设计  10.可爱壁纸 "
                + "\n11.汽车壁纸   12.花卉  13.动物  14.节日  15.人物 \n16.美食     17.水果  18.建筑  19.体育  20.军事 \n21.非主流    22.其它  23.王者荣耀  24.护眼  25.LOL"
                + "\n26.下载以上全部类型\n0.退出\n保存路径:手机/Android/data/com.example.myapplication/files/");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.download:
                downloadButton.setText("下载");
                new Thread() {

                    public void run() {
                        String[] arry = {"rili", "dongman", "fengjing", "meinv", "youxi", "yingshi", "dongtai", "weimei", "sheji", "keai", "qiche", "huahui", "dongwu"
                                , "jieri", "renwu", "meishi", "shuiguo", "jianzhu", "tiyou", "junshi", "feizhuliu", "qita", "s/wangzherongyao", "s/huyan", "s/lol"};
                        int flage = Integer.parseInt(editText1.getText().toString());
                        bian = new BiAn(1, arry[flage - 1], MainActivity.this);
                        bian.isPause= false;
                        try {
                            bian.downLoadPicture();
                            bian.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }.start();
                break;
            case  R.id.pause:
                bian.isPause=true;
                break;
            case  R.id.resume:
                bian.isPause=false;
                break;
        }
    }
    void update(){
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,songlist);
       // listView.setAdapter(adapter);
        ImageViewAdapter base = new ImageViewAdapter(this,list);

        lv1.setAdapter(base);

    }
}
