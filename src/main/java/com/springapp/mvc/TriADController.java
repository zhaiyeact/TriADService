package com.springapp.mvc;

import com.triad.dataobject.ClusterServer;
import com.triad.dataobject.Query;
import com.triad.service.QueryService;
import com.triad.service.RegisterService;
import com.triad.tools.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by zhuoying on 2015/10/2.
 */

@Controller
public class TriADController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    QueryService queryService;

    @Resource
    RegisterService registerService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView QueryPage(ModelMap modelMap,HttpServletRequest request){
        LoadLubmQuries(request);
        List<ClusterServer> masterList = registerService.getMasterList();
        String selectHost = SelectOptionsToView(masterList);
        modelMap.addAttribute("selectHost",selectHost);
        return new ModelAndView("query","query",new Query());
    }

    @RequestMapping(value="/queryexecute",method = RequestMethod.POST)
    public ModelAndView QueryResult(@ModelAttribute("query")Query query,ModelMap modelMap,HttpServletRequest request){
        List<ClusterServer> masterList = registerService.getMasterList();
        String selectHost = SelectOptionsToView(masterList);
        modelMap.addAttribute("selectHost",selectHost);
        try {
            int port = MatchPort(query.getMaster());
            query.setPort(port);
        }
        catch (Exception e){
            logger.error("[TRIAD_CONTROLLER]",e);
        }
        ErrorCode errorCode = queryService.executeQuery(query);
        request.getSession().setAttribute("errorCode", errorCode.getCode());
        modelMap.addAttribute("queryString",query.getRequest());
        if(errorCode.getCode() == ErrorCode.SUCCESS.getCode()){
            //present query result when successfully execute the query
            modelMap.addAttribute("queryResult",query.getResponse());
        }
        return new ModelAndView("query",modelMap);
    }

    @RequestMapping(value = {"/","/cluster"},method = RequestMethod.GET)
    public ModelAndView HomePage(ModelMap modelMap){
        List<ClusterServer> serverList = registerService.getServerList();
        modelMap.addAttribute("mRecordData",RegisterToView(serverList));
        return new ModelAndView("home");
    }

    protected String RegisterToView(List<ClusterServer> serverList){
        StringBuffer sb = new StringBuffer();
        for(ClusterServer server:serverList){
            sb.append("addRecordData(\"clusterTable\",");
            sb.append("\"").append(server.getRegState().getMessage()).append("\",");
            sb.append("\"").append(server.getAddr()).append("\",");
            sb.append("\"").append(server.getName()).append("\",");
            sb.append("\"").append(server.getPort()).append("\",");
            sb.append("\"").append(server.getRole()).append("\");");
        }
        return sb.toString();
    }

    protected int MatchPort(String host) throws Exception{
        List<ClusterServer> masterList = registerService.getMasterList();
        for(ClusterServer master:masterList){
            if(master.getAddr().equals(host))
                return master.getPort();
        }
        throw new Exception("No matching master found!");
    }

    protected String SelectOptionsToView(List<ClusterServer> masterList){
        StringBuffer sb = new StringBuffer();
        for(ClusterServer master:masterList) {
            sb.append("addMasterAddress(");
            sb.append("\"").append(master.getAddr()).append("\",");
            sb.append("\"").append(master.getName()).append("\");");
        }
        return sb.toString();
    }

    protected void LoadLubmQuries(HttpServletRequest request){
        try {
            String absPath = request.getSession().getServletContext().getRealPath("/");
            for (int i = 0; i < 7; i++) {
                Object query = request.getSession().getAttribute("Q"+(i+1));
                if (query == null || query.toString().equals("")) {
                    String path = absPath + "/Quries/Q" + (i+1);
                    File file = new File(path);
                    FileInputStream in = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = null;
                    String queryStr = "";
                    while((line = reader.readLine())!=null){
                        queryStr += line;
                    }
                    request.getSession().setAttribute("Q"+(i+1),queryStr);
                }
            }
        }
        catch (Exception e){
            logger.error("[TRIAD_CONTROLLER] ",e);
        }
    }
}
