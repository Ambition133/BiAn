package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class BiAn extends Thread {
    public static int sum = 0;

    int page;
    String flage;
    boolean isPause=false;
    MainActivity mainActivity;
    Bitmap bitmap = null;
    int i;
    public BiAn(int page, String flage, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.page = page;
        this.flage = flage;
    }
    public void downLoadPicture() throws IOException {
            String adress;
            if (page == 1)
                adress = "http://www.netbian.com/" + flage + "/index.htm";
                //http://www.netbian.com/meinv/index.html"
            else
                adress = "http://www.netbian.com/" + flage + "/index_" + page + ".htm";
            // Jsoup解析器  http://www.netbian.com/meinv/index_2.htm
            Document document = Jsoup.connect(adress).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36").get();//指定URL发送请求并返回document
            // elements标签下的内容    Jsoup解析
            //直接从浏览器复制过来
            Elements elements = document.select("div.wrap.clearfix #main div.list  ul li a");// 选择，元素，标签
            //如果类名有空格继续用.链接

            // 进一步清洗

            for (; i < 21; i++) {// 观察到一个有略缩图的网页一共有30张
                if (!isPause) {
                    Element element = elements.get(i);
                    // 拿到高清图的链接，这个链接在 href 标签里面，attr接收到高清图链接
                    String attr = element.attr("href");
                    if (attr.endsWith("pic.netbian.com/"))//跳过广告
                        continue;
                    String address2 = "http://www.netbian.com" + attr + "";
                    // Jsoup解析器           再进一步的清洗//向高清图网页,发送请求并返回document
                    Document document2 = Jsoup.connect(address2).get();//直接从浏览器复制过来
                    Elements elements2 = document2.select("div.endpage div.pic p a img");// 选择，元素，标签
                    System.out.println(elements2);
                    Element element2 = elements2.get(0);
                    // src有图片的地址，得到了高清图片的地址src.....
                    String address3 = element2.attr("src");
                    String imageName = element2.attr("title");
                    try {
                        // 利用高清图地址发送请求,并设置最大请求的文件大小最大为3M,忽略文件类型,执行请求.得到服务器的回应,
                        //将回应转为字节数组,然后保存到电脑上
                        byte[] bytes = Jsoup.connect(address3).ignoreContentType(true).maxBodySize(60000000).execute().bodyAsBytes();
                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        mainActivity.handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mainActivity.list.add(bitmap);
                                mainActivity.update();
                                mainActivity.textView2.setText("第" + page + "页- " + (i+1) + "张  " + imageName + ".jpg");
                            }
                        });
                        String savePath = mainActivity.getExternalFilesDir(null).getAbsolutePath();
                        File file = new File(savePath + "/" + flage + "");
                        if (!file.exists())
                            file.mkdirs();
                        System.out.println("保存路径" + file.getAbsolutePath());
                        FileOutputStream fileOutputStream = null;
                        fileOutputStream = new FileOutputStream(file.getAbsolutePath() + "/" + page + "-" + (i+1) + "-" + imageName + ".jpg");
                        fileOutputStream.write(bytes);
                        sum++;
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }else {break;}
            }

        if (!isPause) {
            page++;//下一页
            i=0;
            downLoadPicture();
        }
    }
    @Override
    public void run() {
        super.run();
        try {
            while (true)
                downLoadPicture();
        } catch (Exception e) {
        }
    }
}
