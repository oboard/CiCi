package com.oboard.cc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.w3c.dom.Element;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    CircularAnim.fullActivity(MainActivity.this, fab)
                        .colorOrImageRes(R.color.colorAccent)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(MainActivity.this, AddActivity.class));
                            }
                        });
                }
            });
        
        setSupportActionBar(toolbar);

        fresh();
    }

    public void fresh() {
        ArrayList l = new ArrayList();
        TextView v1 = new TextView(this);
        v1.setBackgroundColor(0xFFFFFFFF);
        TextView v2 = new TextView(this);
        v2.setBackgroundColor(0xFF55FFFF);
        l.add(v1);
        l.add(v2);
        viewPager.setAdapter(new MyViewPagerAdapter(l));
    }
    
    

    protected void load() {
        try {
            doc = Jsoup.parse(new URL("http://www.cnbeta.com"), 5000);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Elements es = doc.getElementsByClass("main_navi");
        for (Element e : es) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", e.getElementsByTag("a").text());
            map.put("href", "http://www.cnbeta.com"
                    + e.getElementsByTag("a").attr("href"));
            list.add(map);
        }

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                                              new String[] { "title","href" }, new int[] {
                                                  android.R.id.text1,android.R.id.text2
                                              }));
    }

    public String getHtmlString(String urlString) {
        try {
            URL url = null;
            url = new URL(urlString);

            URLConnection ucon = null;
            ucon = url.openConnection();

            InputStream instr = null;
            instr = ucon.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(instr);

            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            return EncodingUtils.getString(baf.toByteArray(), "gbk");
        } catch (Exception e) {
            return "";
        }
	}
    
    

    public class MyViewPagerAdapter extends PagerAdapter { 

        private ArrayList<View> mListViews;  
        public MyViewPagerAdapter(ArrayList mListView) {  
            mListViews = mListView;//构造方法，参数是我们的页卡，这样比较方便。  
        }  
        @Override public void destroyItem(ViewGroup container, int position, Object object)   {     
            container.removeView(mListViews.get(position));//删除页卡
        }  
        @Override public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡         
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);  
        }  
        @Override  
        public int getCount() {           
            return  mListViews.size();//返回页卡的数量  
        }  
        @Override public boolean isViewFromObject(View arg0, Object arg1) {             
            return arg0 == arg1;//官方提示这样写  
        }  
    } 

}
