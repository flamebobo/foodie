package com.test;

import com.flame.Application;
import com.flame.es.pojo.Stu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void createIndexStu(){
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
}
