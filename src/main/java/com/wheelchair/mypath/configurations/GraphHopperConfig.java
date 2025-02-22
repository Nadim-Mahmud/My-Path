package com.wheelchair.mypath.configurations;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.GraphHopper;
import com.graphhopper.util.GHUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.wheelchair.mypath.constants.Constants.PBF_LOCATION;

/**
 * @author Nadim Mahmud
 * @date 2/21/25
 */
@Configuration
public class GraphHopperConfig {

    @Bean
    public GraphHopper graphHopper() {
        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile(PBF_LOCATION);

        // specify where to store graphhopper files
        hopper.setGraphHopperLocation("target/routing-graph-cache");

        // add all encoded values that are used in the custom model, these are also available as path details or for client-side custom models
        hopper.setEncodedValuesString("car_access, car_average_speed, road_access");

        // see docs/core/profiles.md to learn more about profiles
        hopper.setProfiles(new Profile("car").setCustomModel(GHUtility.loadCustomModelFromJar("car.json")));

        // this enables speed mode for the profile we called car
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        hopper.importOrLoad();
        return hopper;
    }
}