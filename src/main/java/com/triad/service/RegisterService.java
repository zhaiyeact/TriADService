package com.triad.service;

import com.triad.dataobject.ClusterServer;
import com.triad.tools.RegState;

import java.util.List;

/**
 * Created by zhuoying on 2015/11/2.
 */
public interface RegisterService {

    RegState start();

    RegState stop();

    RegState register(String host,String hostName,Integer port,RegState regState,String role);

    List<ClusterServer> getServerList();

    List<ClusterServer> getMasterList();
}
