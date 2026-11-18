package domain;

import java.util.ArrayList;
import java.util.List;

public class ClinicLocationCatalog {

    private List<ClinicLocation> locations = new ArrayList<>();

    public ClinicLocation findLocation(String locationId) {
        for (ClinicLocation location : locations) {
            if (location.getLocationId().equalsIgnoreCase(locationId)) {
                return location;
            }
        }
        return null;
    }

    public ClinicLocation addLocation(String name, String address) {
        ClinicLocation location = new ClinicLocation(generateId(), name, address);
        locations.add(location);
        return location;
    }

    public List<ClinicLocation> getLocations() {
        return locations;
    }

    private String generateId() {
        return "L" + (locations.size() + 1);
    }
}
