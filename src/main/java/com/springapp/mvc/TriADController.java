package com.springapp.mvc;

import com.triad.service.QueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by zhuoying on 2015/10/2.
 */

@Controller
public class TriADController {
    @Resource
    QueryService queryService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String QueryPage(ModelMap modelMap){

        queryService.executeQuery("");

        return "query";
    }

    @RequestMapping(value="/query",method = RequestMethod.POST)
    public String QueryResult(ModelMap modelMap){
        modelMap.get("");
        return "query";
    }
}
