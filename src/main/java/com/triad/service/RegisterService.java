package com.triad.service;

import com.triad.tools.RegState;

/**
 * Created by zhuoying on 2015/11/2.
 */
public interface RegisterService {

    RegState start();

    RegState stop();

    RegState register(String host,Integer port,RegState regState,String role);

}
