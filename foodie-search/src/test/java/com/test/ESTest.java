package com.test;

import com.flame.Application;
import com.flame.es.pojo.Stu;
import com.flame.es.pojo.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *<p>Title:</p>
 *<p>Description:</p>
 *<p>Copyright: Copyright (c) 2021</p>
 *<p>Company：PCCW</p>
 *
 *@description:
 *@author: Flame.Lai
 *@version 1.0
 *@time: 2021/9/6 11:55
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 不建议使用 ElasticsearchTemplate 对索引进行管理（创建索引，更新映射，删除索引）
     * 索引就像是数据库或者数据库中的表，我们平时是不会通过java代码频繁的去创建修改删除数据库或表的
     * 我们只会针对数据做CRUD的操作
     * 在ES中也是同理，我们尽量使用elasticsearchTemplate对文档数据做CRUD的操作
     * 1、属性（FieldType）类型不灵活
     * 2、主分片与副本分片数无法设置
     *
     */

    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1001L);
        stu.setAge(18);
        stu.setName("张三");
        stu.setDesc("这是描述");
        stu.setMoney(1000L);
        stu.setDescription("这是描述~~~");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    @Test
    public void createIndexStuv1() {
        Stu stu = new Stu();
        stu.setStuId(1002L);
        stu.setAge(18);
        stu.setName("小明");
        stu.setDesc("这是描述");
        stu.setMoney(1123123);
        stu.setDescription("这是描述~~~");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);
    }

    @Test
    public void deleteIndexStu() {
        esTemplate.deleteIndex(Stu.class);
    }


    // ----------------------------对文档的操作----------------


    @Test
    public void updateStuDoc() {
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("sign", "I am not super man");
        sourceMap.put("money", 88.6f);
        sourceMap.put("age", 33);
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(sourceMap);
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId("1001")
                .withIndexRequest(indexRequest)
                .build();
            // update stu set sign=' abc' , age=33, money=8n6源hdpe\docId=' 1002'
        esTemplate.update(updateQuery);
    }

    @Test
    public void getStuDoc() {
        GetQuery getQuery = new GetQuery();
        getQuery.setId("1001");
        Stu stu = esTemplate.queryForObject(getQuery, Stu.class);
        System.out.println("stu= " + stu);
    }

    @Test
    public void delStuDoc() {
        esTemplate.delete(Stu.class, "1001");
    }

    // -----------------


    /**
     * 查询doc
     */
    @Test
    public void searchStuDoc(){
        PageRequest pageRequest = PageRequest.of(0, 10);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "小明"))
                .withPageable(pageRequest)
                .build();

        AggregatedPage<Stu> pagedStu= esTemplate.queryForPage(searchQuery, Stu.class);
        System.out.println("检索后的总分页数目为：" + pagedStu.getTotalPages());
        List<Stu> content = pagedStu.getContent();
        for (Stu stu : content) {
            System.out.println(stu);
        }

    }
}
