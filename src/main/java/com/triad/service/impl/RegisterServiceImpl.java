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
 * run/stop/initialize ${host} ${hostName} ${port} ${role}
 */
public class RegisterServiceImpl implements RegisterService {

    private static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    private String webHost;

    private Integer webPort;

    private List<ClusterServer> serverList;

    private static volatile RegisterService instance;


    private Socket socket;


    public String getWebHost() {
        return webHost;
    }

    public void setWebHost(String webHost) {
        logger.info("[REGISTER_SERVICE] setWebHost invoked");
        this.webHost = webHost;
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }

    public List<ClusterServer> getServerList() {
        return serverList;
    }


    private RegisterServiceImpl(){}

    public static RegisterService getInstance(){
        if(instance == null){
            synchronized (RegisterServiceImpl.class){
                if(instance == null){
                    try{
                        instance =  new RegisterServiceImpl();
                    }
                    catch (Exception e){
                        logger.error("[REGISTER_SERVICE] initiation error");
                    }
                }
            }
        }
        return instance;
    }

    public void init(){
        start();
    }

    @Override
    public RegState start() {
        synchronized (RegisterServiceImpl.class) {
            if (instance != null) {
                //return if the register is running or initializing
                return RegState.RUN;
            }
        }
        getInstance();
        serverList = new ArrayList<ClusterServer>();
        Runnable serverThread = new Runnable() {
            @Override
            public void run() {
                try {
                    ExecutorService executor = Executors.newFixedThreadPool(20);
                    logger.info("[REGISTER_SERVICE] start register server!");
                    while (true)
                    {
                        if(instance==null) {
                            synchronized (RegisterServiceImpl.class) {
                                if (instance == null) {
                                    break;
                                }
                            }
                        }
                        socket = new ServerSocket(webPort).accept();
                        executor.execute(new WorkerThread(socket));
                    }

                }
                catch (IOException e){
                    logger.error("[REGISTER_SERVICE] IOException ",e);
                }
                finally {
                    try {
                        socket.close();
                    }
                    catch (IOException e){

                    }
                }
            }
        };
        new Thread(serverThread).start();
        logger.info("[REGISTER_SERVICE] FINISHED INITIATION");
        return RegState.STOP;
    }

    @Override
    public RegState stop() {
        return null;
    }

    @Override
    public RegState register(String host, String hostName,Integer port,RegState regState,String role) {
        ClusterServer server = new ClusterServer();
        server.setPort(port);
        server.setAddr(host);
        server.setName(hostName);
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
        if(role.toLowerCase().equals(ClusterServer.MASTER.toLowerCase())){
            return ClusterServer.MASTER;
        }
        else if(role.toLowerCase().equals(ClusterServer.SLAVE.toLowerCase())){
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
                logger.info("[REGISTER_SERVICE] socket connection established");
                Reader reader = new InputStreamReader(client.getInputStream());
                char result[] = new char[1024];
                StringBuilder sb = new StringBuilder();
                int len;
                while ((len = reader.read(result, 0, 1024)) != -1) {
                    sb.append(result, 0, len);
                }
                String msg = sb.toString();
                if(msg.equals("stop")){
                    synchronized (RegisterServiceImpl.class){
                        instance = null;
                    }
                    return;
                }
                String[] lines = msg.split("\n");
                for(String line:lines){
                    String[] opts = line.split(" ");
                    String operation = opts[0];
                    String address = opts[1];
                    String name = opts[2];
                    String port = opts[3];
                    String role = opts[4];
                    register(address,name,Integer.parseInt(port),matchState(operation),matchRole(role));
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
