package com.triad.dataobject;

import com.triad.tools.RegState;

import java.io.Serializable;

/**
 * Created by zhuoying on 2015/11/2.
 */
public class ClusterServer implements Serializable {
    private static final long serialVersionUID = 5752825957009424339L;

    public static String MASTER ="MASTER";

    public static String SLAVE = "SLAVE";

    private String addr;

    private String name;

    private Integer port;

    private RegState regState;

    private String role;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public RegState getRegState() {
        return regState;
    }

    public void setRegState(RegState regState) {
        this.regState = regState;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
