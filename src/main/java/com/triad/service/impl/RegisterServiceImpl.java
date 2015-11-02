package com.triad.service.impl;

import com.triad.dataobject.ClusterServer;
import com.triad.service.RegisterService;
import com.triad.tools.RegState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuoying on 2015/11/2.
 * The register message format shows as follows:
 * run/stop/initialize ${host} ${port} ${role}
 */
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String webHost;

    private Integer webPort;

    private RegState regState;

    private List<ClusterServer> serverList;


    private Socket socket;

    public RegState getRegState() {
        return regState;
    }

    public void setRegState(RegState regState) {
        this.regState = regState;
    }

    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        webHost = webHost;
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setWebPort(Integer webPort) {
        webPort = webPort;
    }



    @Override
    public RegState start() {
        if(getRegState().getCode() == RegState.RUN.getCode()){
            //return if the register is running or initializing
            return getRegState();
        }
        try {
            setRegState(RegState.RUN);
            serverList = new ArrayList<ClusterServer>();
            ExecutorService executor = Executors.newFixedThreadPool(20);

            while(true){
                if(getRegState().getCode() == RegState.STOP.getCode()){
                    break;
                }
                socket = new ServerSocket(getWebPort()).accept();
                executor.execute(new WorkerThread(socket));
            }
        }
        catch(IOException e){
            logger.error("[REGISTER_SERVICE] IOException ",e);
        }
        return RegState.STOP;
    }

    @Override
    public RegState stop() {
        return null;
    }

    @Override
    public RegState register(String host, Integer port,RegState regState,String role) {
        ClusterServer server = new ClusterServer();
        server.setPort(port);
        server.setAddr(host);
        server.setRegState(regState);
        Boolean contains = false;
        for(ClusterServer clusterServer:serverList){
            if(clusterServer.getAddr().equals(host) && clusterServer.getRole().equals(role) && clusterServer.getPort().equals(port)){
                clusterServer.setRegState(regState);
                contains = true;
            }
        }
        if(!contains){
            serverList.add(server);
        }
        return regState;
    }

    protected RegState matchState(String operation) throws Exception{
        if(operation.equals("initialize"))
            return RegState.INITIALIZING;
        else if(operation.equals("run"))
            return RegState.RUN;
        else if(operation.equals("stop"))
            return RegState.STOP;
        else {
            throw new Exception(operation+ " is undefined!");
        }
    }

    protected String matchRole(String role) throws Exception{
        if(role.toLowerCase().equals("master")){
            return ClusterServer.MASTER;
        }
        else if(role.toLowerCase().equals("slave")){
            return ClusterServer.SLAVE;
        }
        else{
            throw new Exception(role+" is undefined!");
        }
    }

    class WorkerThread implements Runnable{

        private Socket client;

        WorkerThread(final Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            try {
                Reader reader = new InputStreamReader(client.getInputStream());
                char result[] = new char[1024];
                StringBuilder sb = new StringBuilder();
                int len;
                while ((len = reader.read(result, 0, 1024)) != -1) {
                    sb.append(result, 0, len);
                }
                String msg = sb.toString();
                if(msg.equals("stop")){
                    setRegState(RegState.STOP);
                    return;
                }
                String[] lines = msg.split("\n");
                for(String line:lines){
                    String[] opts = line.split(" ");
                    String operation = opts[0];
                    String address = opts[1];
                    String port = opts[2];
                    String role = opts[3];
                    register(address,Integer.parseInt(port),matchState(operation),matchRole(role));
                }
            }
            catch (IOException e){
                logger.error("[REGISTER_SERVICE] IOException ",e);
            }
            catch (Exception e){
                logger.error("[REGISTER_SERVICE] An error occurred ",e);
            }
        }
    }
}
