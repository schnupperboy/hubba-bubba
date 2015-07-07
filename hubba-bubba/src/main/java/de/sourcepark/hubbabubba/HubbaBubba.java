package de.sourcepark.hubbabubba;

import de.sourcepark.hubbabubba.services.CandyService;
import de.sourcepark.hubbabubba.services.ExampleCandyService;
import de.sourcepark.hubbabubba.services.HTTPMethod;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;
import static spark.Spark.get;
import static spark.SparkBase.port;

/**
 * The actual main (server) class.
 * @author smatyba
 */
public class HubbaBubba {
    /**
     * Logger.
     */
    private static final transient Logger LOG = LoggerFactory.getLogger(HubbaBubba.class);
    
    /**
     * Starts a new server on a port and with the given services.
     * @param port The port the server is supposed to listen on.
     * @param services The services the server offers.
     */
    public final void startServer(final int port, final CandyService... services) {
        port(port);
        
        LOG.info("Starting server on port {}", port);
        
        for(final CandyService service : services) {
            LOG.info("Registering candy service '{}'...", service.getName());
            for(final Map.Entry<HTTPMethod, Map<String, Route>> entry : service.getRoutes().entrySet()) {
                switch(entry.getKey()) {
                    case GET: { 
                        for(final Map.Entry<String, Route> mapping : entry.getValue().entrySet()) {
                            get(mapping.getKey(), mapping.getValue());
                        }
                        break;
                    }
                }
            }
        }
    }
}