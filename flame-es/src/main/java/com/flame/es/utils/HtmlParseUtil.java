package com.flame.es.utils;

import com.flame.es.pojo.Content;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author Flame.Lai
 * @date 2023-02-06 22:52
 */
@Slf4j
public class HtmlParseUtil {

    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().parseJD("java").forEach(System.out::println);
    }

    public List<Content> parseJD(String keyword) throws Exception {
        if(StringUtil.isNullOrEmpty(keyword)){
            keyword = "";
        }
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        Document document = Jsoup.parse(new URL(url), 30000);
        Element goodsList = document.getElementById("J_goodsList");
        Elements elements = goodsList.getElementsByTag("li");
        ArrayList<Content> resultList = new ArrayList<>();
        log.info("总条数：{}", elements.size());
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();

            Content content = new Content();
            content.setPrice(price);
            content.setTitle(title);
            content.setImg(img);
            resultList.add(content);
        }
        return resultList;
    }

}
