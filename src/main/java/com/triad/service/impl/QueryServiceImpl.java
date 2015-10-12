package com.triad.service.impl;

import com.triad.dataobject.Query;
import com.triad.service.QueryService;
import com.triad.tools.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by zhuoying on 2015/10/1.
 */
public class QueryServiceImpl implements QueryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ErrorCode executeQuery(Query query) {
        if(query==null || StringUtils.isEmpty(query.getRequest()))
            return ErrorCode.EMPTY_QUERY;
        try {
            //for debugging
            query.setMaster("192.168.7.3");
            query.setPort(1919);

            Socket client = new Socket(query.getMaster(), query.getPort());
            //sending query request
            Writer writer = new OutputStreamWriter(client.getOutputStream());
            writer.write(query.getRequest().length()+"",0,32);
            int off = 0;
            int maxSize = query.getRequest().length();
            while(off<maxSize){
                int len;
                if(off+1024<maxSize){
                    len = 1024;
                }
                else
                    len = maxSize - off;
                writer.write(query.getRequest(),off,len);
                off+=len;
            }

            //receiving result
            Reader reader = new InputStreamReader(client.getInputStream());
            char result[] = new char[1024];
            StringBuilder sb = new StringBuilder();
            int len;
            while((len=reader.read(result,0,1024))!=-1){
                sb.append(result,0,len);
            }
            String response = sb.toString();
            logger.debug("[QUERY_SERVICE] result: \n"+ response);
            query.setResponse(response);
        }
        catch (IOException e){
            logger.error("[QUERY_SERVICE] socket IO exception",e);
            return ErrorCode.SOCKET_ERROR;
        }
        return ErrorCode.SUCCESS;
    }


}
