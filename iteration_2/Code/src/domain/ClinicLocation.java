package domain;

public class ClinicLocation {

    private String locationId;
    private String name;
    private String address;

    public ClinicLocation(String locationId, String name, String address) {
        this.locationId = locationId;
        this.name = name;
        this.address = address;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return locationId + "  " + name + ", " + address;
    }
}
