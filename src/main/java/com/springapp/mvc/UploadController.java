package com.springapp.mvc;

import com.triad.dataobject.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhuoying on 2015/12/7.
 */
@Controller
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/uploadQuery",method = RequestMethod.POST)
    public ModelAndView UploadQuery(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request,ModelMap modelMap){
        try {
            if (!file.isEmpty()) {
                InputStream in = file.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                String queryStr = "";
                while((line = reader.readLine())!=null){
                    queryStr += line;
                }
                Query query = new Query();
                query.setRequest(queryStr);
                modelMap.addAttribute("queryString",queryStr);
                return new ModelAndView("query");
            }
        }
        catch (Exception e){
            logger.error("[UPLOADCONTROLLER] Exception ",e);
        }
        return new ModelAndView("query");
    }
}
