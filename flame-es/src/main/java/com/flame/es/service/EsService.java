package com.flame.es.service;

import com.alibaba.fastjson2.JSON;
import com.flame.es.pojo.Content;
import com.flame.es.utils.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Flame.Lai
 * @date 2023-02-07 00:47
 */
@Service
public class EsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public Boolean parseContent(String keyword) throws Exception {
        List<Content> contents = new HtmlParseUtil().parseJD(keyword);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");

        for (Content content : contents) {
            bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(content), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    public List<Map<String, Object>> searchPage(String keyword, int pageNum, int pageSize) throws IOException {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        // 条件搜索
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 分页
        sourceBuilder.from(pageNum);
        sourceBuilder.size(pageSize);
        // 精确匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        // 执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        ArrayList<Map<String, Object>> resultList = new ArrayList<>();
        for (SearchHit hit : search.getHits().getHits()) {
            // 解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if (title != null) {
                Text[] fragments = title.fragments();
                String newTitle = "";
                for (Text fragment : fragments) {
                    newTitle += fragment;
                }
                sourceAsMap.put("title", newTitle);
            }
            resultList.add(sourceAsMap);
        }
        return resultList;
    }
}
