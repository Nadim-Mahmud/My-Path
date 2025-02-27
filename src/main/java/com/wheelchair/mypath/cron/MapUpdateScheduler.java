package com.wheelchair.mypath.cron;

import com.wheelchair.mypath.configurations.CustomGraphHopperConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.wheelchair.mypath.constants.Constants.*;
import static com.wheelchair.mypath.utils.Utils.deleteDirectory;
import static com.wheelchair.mypath.utils.Utils.downloadFile;

/**
 * @author Nadim Mahmud
 * @date 2/27/25
 */
@Component
@EnableScheduling
public class MapUpdateScheduler {
    private static final Logger logger = LoggerFactory.getLogger(MapUpdateScheduler.class);

    @Autowired
    private CustomGraphHopperConfig graphHopperConfig;

    @Scheduled(cron = "0 0 2 * * MON") // Runs every Monday at 2:00 AM
    public void updateMapWeekly() {
        try {
            logger.info("Scheduled weekly map update started");

            String newPbfPath = downloadFile(PBF_URL, PBF_PATH);
            logger.info("Downloaded new .pbf file from {} to {}", PBF_URL, PBF_PATH);

            graphHopperConfig.updateMap(newPbfPath);

            deleteDirectory(PBF_PATH);
            logger.info("Scheduled weekly map update completed successfully");
        } catch (Exception e) {
            logger.error("Failed to perform weekly map update: {}", e.getMessage(), e);
        }
    }
}