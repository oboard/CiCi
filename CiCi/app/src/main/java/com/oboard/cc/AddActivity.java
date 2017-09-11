package com.oboard.cc;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.arlib.floatingsearchview.FloatingSearchView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import android.net.Uri;
import java.net.URL;
import org.jsoup.nodes.Element;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FloatingSearchView fsv;
        fsv = (FloatingSearchView)findViewById(R.id.add_fsv);
        fsv.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() { 
                public void onHomeClicked() {
                    finish();
                }
            });
        fsv.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
                public void onSearchAction(final String s) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                MainActivity.t.setText(loadWord(s));
                            } catch (Exception e) {
                            }
                        }
                    }.start();

                    finish();
                }
                public void onSuggestionClicked(com.arlib.floatingsearchview.suggestions.model.SearchSuggestion ss) {

                }
            });
    }

    public String loadWord(String str) {
        try {
            //从一个URL加载一个Document对象。
            Document doc = Jsoup.parse(new URL("http://hanyu.baidu.com/s?wd=" + str), 5000);
            
            String header = doc.html().split("header-list\">")[1];
            
            //Pin Yin
            String py = header.split("id=\"pinyin\">")[1].split("<b>")[1].split("</b>")[0];
            
            //Bu Shou
            String bs = header.split("id=\"radical\">")[1].split("<span>")[1].split("</span>")[0];

            //Wu Bi
            String wb = header.split("id=\"wubi\"")[1].split("<span>")[1].split("</span>")[0];
            
            //Bi Hua
            String bh = header.split("id=\"stroke_count\">")[1].split("<span>")[1].split("</span>")[0];
            return "拼音 " + py + "笔画 " + bh + "部首 " + bs + "五笔 " + wb ;
        } catch (Exception e) {
        }
        return "";
    }

}
