package com.springapp.mvc;

import com.triad.dataobject.ClusterServer;
import com.triad.dataobject.Query;
import com.triad.service.QueryService;
import com.triad.service.RegisterService;
import com.triad.tools.ErrorCode;
import com.triad.tools.RegState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public ModelAndView QueryPage(){
        return new ModelAndView("query","query",new Query());
    }

    @RequestMapping(value="/queryexecute",method = RequestMethod.POST)
    public ModelAndView QueryResult(@ModelAttribute("query")Query query,ModelMap modelMap,HttpServletRequest request){
        //logger.debug("[CONTROLLER] query: "+query.getRequest());
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
        List<ClusterServer> serverList = forkCluster();
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

    protected List<ClusterServer> forkCluster(){
        List<ClusterServer> serverList = new ArrayList<ClusterServer>();
        ClusterServer server1 = new ClusterServer();
        server1.setName("server01");
        server1.setAddr("127.0.0.1");
        server1.setPort(8888);
        server1.setRegState(RegState.RUN);
        server1.setRole("master");
        serverList.add(server1);

        ClusterServer server2 = new ClusterServer();
        server2.setName("server02");
        server2.setAddr("127.0.0.2");
        server2.setPort(8888);
        server2.setRegState(RegState.STOP);
        server2.setRole("slave");
        serverList.add(server2);
        return serverList;
    }

}
