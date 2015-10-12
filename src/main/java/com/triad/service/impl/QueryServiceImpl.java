package com.triad.service.impl;

import com.triad.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuoying on 2015/10/1.
 */
public class QueryServiceImpl implements QueryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String executeQuery(String query) {
        logger.info("[QUERY_SERVICE] executeQuery invoked! ");
        return null;
    }
}
