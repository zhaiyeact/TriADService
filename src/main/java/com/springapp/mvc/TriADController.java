package com.springapp.mvc;

import com.triad.dataobject.Query;
import com.triad.service.QueryService;
import com.triad.tools.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by zhuoying on 2015/10/2.
 */

@Controller
public class TriADController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    QueryService queryService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView QueryPage(){

        return new ModelAndView("query","query",new Query());
    }

    @RequestMapping(value="/queryexecute",method = RequestMethod.POST)
    public String QueryResult(@ModelAttribute("query")Query query,ModelMap modelMap){
        //logger.debug("[CONTROLLER] query: "+query.getRequest());
        ErrorCode errorCode = queryService.executeQuery(query);
        if(errorCode.getCode() == ErrorCode.SUCCESS.getCode()){
            //present query result when successfully execute the query
            modelMap.addAttribute("queryResult",query.getResponse());
        }
        return "query";
    }

    @RequestMapping(value = {"/home","cluster"},method = RequestMethod.GET)
    public ModelAndView HomePage(){
        logger.debug("[CONTROLLER] home requested");
        return new ModelAndView("home");
    }

}
