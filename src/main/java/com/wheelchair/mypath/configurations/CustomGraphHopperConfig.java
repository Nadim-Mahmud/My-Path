package com.wheelchair.mypath.configurations;

import com.graphhopper.GraphHopperConfig;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.GraphHopper;
import com.graphhopper.reader.dem.SRTMProvider;
import com.wheelchair.mypath.utils.GHProfileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.wheelchair.mypath.constants.Constants.*;
import static com.wheelchair.mypath.model.CustomProfiles.WHEEL_CHAIR;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@Configuration
public class CustomGraphHopperConfig {

    @Bean
    public GraphHopper graphHopper() {
        GraphHopperConfig graphHopperConfig = new GraphHopperConfig();
        graphHopperConfig.putObject("graph.dataaccess.default_type", "MMAP_STORE");

        GraphHopper hopper = new GraphHopper();

        hopper.setOSMFile(PBF_PATH);
        hopper.setGraphHopperLocation(GH_CACHE_PATH);

        hopper.setEncodedValuesString("foot_access, hike_rating, mtb_rating, foot_priority, foot_average_speed, average_slope, max_slope, surface, footway, smoothness, country, road_class");
        hopper.setElevationProvider(new SRTMProvider());

        Profile wheelChair = new Profile(WHEEL_CHAIR.getLabel()).setCustomModel(GHProfileUtils.loadCustomModel(WHEELCHAIR_CUSTOM_MODEL_PATH));

        hopper.setProfiles(wheelChair);
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile(WHEEL_CHAIR.getLabel()));

        hopper.importOrLoad();

        return hopper;
    }
}