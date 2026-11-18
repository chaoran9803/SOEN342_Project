package domain;

public class Service {

    private String serviceId;
    private String name;
    private boolean requiresReferral;
    private boolean isInsured;

    public Service(String serviceId, String name, boolean requiresReferral, boolean isInsured) {
        this.serviceId = serviceId;
        this.name = name;
        this.requiresReferral = requiresReferral;
        this.isInsured = isInsured;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public boolean requiresReferral() {
        return requiresReferral;
    }

    public boolean isInsured() {
        return isInsured;
    }

    @Override
    public String toString() {
        return serviceId + "  " + name + (requiresReferral ? " (referral required)" : "") + (isInsured ? "" : " (uninsured)");
    }
}
