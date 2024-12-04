package com.telamin.app.pnl;

import com.fluxtion.server.FluxtionServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleMain {

    private static final Logger eventAudit = LogManager.getLogger("eventAudit");

    public static void main(String[] args) {
        eventAudit.warn("#STARTING FLUXTION SERVER");
        FluxtionServer fluxtionServer = FluxtionServer.bootServer(eventAudit::info);
    }
}
