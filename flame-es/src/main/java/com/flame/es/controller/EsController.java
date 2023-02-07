package com.flame.es.controller;

import com.flame.es.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Flame.Lai
 * @date 2023-02-07 00:47
 */
@RestController
public class EsController {

    @Autowired
    private EsService esService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword) throws Exception{
       return esService.parseContent(keyword);
    }

    @GetMapping("/searchPage/{keyword}/{pageNum}/{pageSize}")
    public List<Map<String, Object>> searchPage(@PathVariable("keyword") String keyword,
                                                @PathVariable("pageNum") int pageNum,
                                                @PathVariable("pageSize") int pageSize) throws IOException {
        return esService.searchPage(keyword, pageNum, pageSize);
    }
}
