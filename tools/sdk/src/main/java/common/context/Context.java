//-------------------------------------------------------------------------------------
package xpu.sw.tools.sdk.common.context;
//-------------------------------------------------------------------------------------
import java.io.*;
import java.net.*;
import java.time.ZoneId;
import java.nio.charset.*;
import java.util.*;

import org.apache.commons.cli.*;
import org.apache.commons.configuration2.*;
import org.apache.logging.log4j.*;



import codex.common.utils.*;
import codex.common.wrappers.config.*;
import codex.common.wrappers.iot.*;
import codex.common.wrappers.logs.*;
import codex.common.wrappers.version.*;

import xpu.sw.tools.sdk.*;
import xpu.sw.tools.sdk.rexec.remotehandler.stack.NetworkLayer;

//-------------------------------------------------------------------------------------
public class Context {
    private Sdk sdk;
    private Logger log;
    private CommandLine commandLine;
    private ConfigurationContainer configurationContainer;
    private Configuration sdkConfig;
    private Configuration xpuConfig;
    private Version version;
    private String sessionId;
    private long startTime;
    private String pathToSdkHome;
    private boolean debugStatus;
    private int state;
    private String hostIP;
    private int port;

//--- XPU Config
    private int nCells;

    private int memDataArraySizeLog;
    private int memCodeArraySizeLog;
    private int memCodeControllerSizeLog;
    private int memFeatureSizeLog;

    public static final String PROFILE_APP_LEVEL                = "AppLevel";
    public static final String PROFILE_HIGH_LEVEL               = "HighLevel";
    public static final String PROFILE_LOW_LEVEL                = "LowLevel";

    public static final int CONTEXT_STATE_INIT          = 0;
    public static final int CONTEXT_STATE_RUNNING       = 1;

    public static final boolean DEBUG_STATUS_OFF    = false;
    public static final boolean DEBUG_STATUS_ON     = true;

//-------------------------------------------------------------------------------------
    public Context(Sdk _sdk, Logger _log, CommandLine _commandLine) {
        sdk = _sdk;
        log = _log;
        commandLine = _commandLine;

        hostIP = "zeus.arh.pub.ro";
        port = 49000;

        TimeUtils.setZoneId(ZoneId.of("Europe/Bucharest"));
        pathToSdkHome = System.getProperty("user.home") + "/.xpu";
        version = new Version(log, new String[]{"xpu-sdk-libs-", "xpu-sdk-"});
//        log.info("Starting Sdk v" + getVersion() + " ...");

//        String _daoConfigurationPath = SystemUtils.getHomeDirectory() + "/.sdk/etc/";
        configurationContainer = new ConfigurationContainer(log, pathToSdkHome + "/etc/");
        sdkConfig = configurationContainer.getConfiguration("sdk");
        xpuConfig = configurationContainer.getConfiguration("xpu");
        
        sessionId = TimeUtils.getTimeAsString();
        startTime = System.currentTimeMillis();
        state = CONTEXT_STATE_INIT;
        debugStatus = sdkConfig.getBoolean("debug", false);
        nCells = xpuConfig.getInt("nCells", 1024);
        memCodeArraySizeLog = xpuConfig.getInt("memCodeArraySizeLog", 10);

        memCodeControllerSizeLog = xpuConfig.getInt("memCodeControllerSizeLog", 10);
        memDataArraySizeLog = xpuConfig.getInt("memDataArraySizeLog", 20);
        memFeatureSizeLog = xpuConfig.getInt("memFeatureSizeLog", 1);

        log.debug("Machine parameters: nCells=" + nCells +
                ", memCodeControllerSizeLog=" + memCodeControllerSizeLog +
                ", memCodeArraySizeLog=" + memCodeArraySizeLog +
                ", memDataArraySizeLog=" + memDataArraySizeLog +
                ", memFeatureSizeLog=" + memFeatureSizeLog
        );
    }
/*
//-------------------------------------------------------------------------------------
    public Context(Context _context, Class _class) {
        sdk = _context.sdk;
        commandLine = _context.commandLine;

//        LogLoader _logLoader = new LogLoader();
//        log = LogManager.getLogger(Dao.class);
        log = _context.log;
//        log = LogUtil.getLogger(_class);

        version = _context.version;

        configurationContainer = _context.configurationContainer;
        sdkConfig = _context.sdkConfig;
        xpuConfig = _context.xpuConfig;

        sessionId = _context.sessionId;
        startTime = _context.startTime;
        debugStatus = sdkConfig.getBoolean("debug", false);
    }
*/
//-------------------------------------------------------------------------------------
    public Sdk getSdk(){
        return sdk;
    }

//-------------------------------------------------------------------------------------
    public Logger getLog() {
        return log;
    }

//-------------------------------------------------------------------------------------
    public void setLog(Logger _log) {
        log = _log;
    }

//-------------------------------------------------------------------------------------
    public String getProfile(){
        return getSdkConfig().getString("profile", PROFILE_APP_LEVEL);
    }

//-------------------------------------------------------------------------------------
    public CommandLine getCommandLine() {
        return commandLine;
    }

//-------------------------------------------------------------------------------------
    public ConfigurationContainer getConfigurationContainer(){
        return configurationContainer;
    }

//-------------------------------------------------------------------------------------
    public Configuration getSdkConfig(){
        return sdkConfig;
    }

//-------------------------------------------------------------------------------------
    public Configuration getXpuConfig(){
        return xpuConfig;
    }

//-------------------------------------------------------------------------------------
    public Version getVersionObject(){
        return version;
    }

//-------------------------------------------------------------------------------------
    public String getVersion(){
        return "v" + version.getVersion(1);
    }

//-------------------------------------------------------------------------------------
    public String getSessionId(){
        return sessionId;
    }

//-------------------------------------------------------------------------------------
    public long getStartTime(){
        return startTime;
    }

//-------------------------------------------------------------------------------------
    public synchronized int getState(){
        return state;
    }

//-------------------------------------------------------------------------------------
    public synchronized int setState(int _state){
        return state = _state;
    }

//-------------------------------------------------------------------------------------
    public String getPathToSdkHome(){
        return pathToSdkHome;
    }
    
//-------------------------------------------------------------------------------------
    public boolean getDebugStatus(){
        return debugStatus;
    }
    
//-------------------------------------------------------------------------------------
    public void setDebugStatus(boolean _debugStatus){
        debugStatus = _debugStatus;
        sdkConfig.setProperty("debug", _debugStatus);
//        log.debug("_debugStatus=" + _debugStatus);
//        new Throwable().printStackTrace();
    }

//-------------------------------------------------------------------------------------
// XPU configs
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
    public int getNCells(){
        return nCells;
    }
    
//-------------------------------------------------------------------------------------
    public int getMemDataArraySizeLog(){ return memDataArraySizeLog; }

//-------------------------------------------------------------------------------------
    public int getMemCodeControllerSizeLog(){
        return memCodeControllerSizeLog;
    }

//-------------------------------------------------------------------------------------
    public int getMemCodeArraySizeLog(){ return memCodeArraySizeLog; }

//-------------------------------------------------------------------------------------
    public int getMemFeatureSizeLog(){ return memFeatureSizeLog; }

    //-------------------------------------------------------------------------------------
    public void save() {
        configurationContainer.save();
    }

//-------------------------------------------------------------------------------------
    public String getHost() {
        return hostIP;
    }

//-------------------------------------------------------------------------------------
    public void setHost(String _hostIP) {
        hostIP = _hostIP;
    }

//-------------------------------------------------------------------------------------
    public int getPort() {
        return port;
    }

//-------------------------------------------------------------------------------------
    public void setPort(int _Port) {
        port = _Port;
    }
    
//-------------------------------------------------------------------------------------
}
//-------------------------------------------------------------------------------------
