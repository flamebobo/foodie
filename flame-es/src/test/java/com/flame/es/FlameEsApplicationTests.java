package com.flame.es;

import com.flame.es.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson2.JSON;
/**
 * es 7.6.X 高级客户端测试API
 */
@SpringBootTest
class FlameEsApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    // 测试索引的创建
    @Test
    void testCreateIndex() throws IOException {
        // 1、 创建请求
        CreateIndexRequest request = new CreateIndexRequest("flame_index");
        // 2、执行请求,请求后获得响应
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.toString());
    }

    // 测试获取索引
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("flame_index");
        // 判断索引是否存在
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
        // 2、执行请求,请求后获得响应
        GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
        System.out.println(getIndexResponse);
    }

    // 测试删除索引
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("flame_index");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    // 测试添加文档
    @Test
    void testCreateDocument() throws IOException {
        // 创建对象
        User user = new User("flame", 12);
        // 创建请求
        IndexRequest request = new IndexRequest("flame_index");
        // 规则 put /flame_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        // 将我们的请求放入json,
        request.source(JSON.toJSONString(user), XContentType.JSON);
        // 客户端发送请求，获得相应的结果
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
    }

    // 获取文档，判断是否存在 get /index/doc/doc_id
    @Test
    void testDocIsExists() throws IOException{
        GetRequest request = new GetRequest("flame_index", "1");
        // 不获取返回的 _source的上下文了
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 获取文档的信息
    @Test
    void testGetDocInfo() throws IOException{
        GetRequest request = new GetRequest("flame_index", "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 打印文档的内容
        System.out.println(response.getSourceAsString());
        // 返回的全部内容和命令是一样的
        System.out.println(response);
    }

    // 更新文档的信息
    @Test
    void testdUpdateDocInfo() throws IOException{
        UpdateRequest request = new UpdateRequest("flame_index", "1");
        request.timeout("1s");

        User user = new User("flame很帅气的", 18);
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        // 客户端发送请求，获得相应的结果
        UpdateResponse update = client.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    // 测试删除文档
    @Test
    void testDeleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest("flame_index","1");
        request.timeout("1s");
        DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    // 特殊的，真的项目一般都是批量插入数据
    @Test
    void testBulkInsert() throws IOException{
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("flame1", 18));
        userList.add(new User("flame2", 18));
        userList.add(new User("flame3", 18));
        userList.add(new User("flame4", 18));
        userList.add(new User("flame5", 18));
        userList.add(new User("flame6", 18));
        userList.add(new User("flame7", 18));
        userList.add(new User("flame8", 18));
        userList.add(new User("flame9", 18));
        userList.add(new User("flame10", 18));
        userList.add(new User("flame11", 18));
        userList.add(new User("flame12", 18));
        userList.add(new User("flame13", 18));
        userList.add(new User("flame14", 18));
        userList.add(new User("flame15", 18));
        userList.add(new User("flame16", 18));

        for (int i = 0; i < userList.size(); i++) {
            // 批量插入，批量更新、批量删除，就在这里操作就可以了
            bulkRequest.add(new IndexRequest("flame_index")
                    .id(""+(i+1))
                    .source(JSON.toJSONString(userList.get(i)), XContentType.JSON));
        }

        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 是否失败
        System.out.println(bulk.hasFailures());
    }

    // 复杂查询
    @Test
    void testSearch() throws IOException {
        SearchRequest index = new SearchRequest("flame_index");
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /**
         *  查询条件，我们可以使用 QueryBuilders 工具来实现
         *  QueryBuilders.termQuery 精确
         *  QueryBuilders.matchAllQuery() 匹配所有
         */
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "flame1");
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        index.source(sourceBuilder);

        SearchResponse search = client.search(index, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        System.out.println("========================");
        for (SearchHit documentFields : search.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
    }

}
