package domain;

import java.util.ArrayList;
import java.util.List;

public class ServiceCatalog {

    private List<Service> services = new ArrayList<>();

    public Service findService(String serviceId) {
        for (Service service : services) {
            if (service.getServiceId().equalsIgnoreCase(serviceId)) {
                return service;
            }
        }
        return null;
    }

    public Service addService(String name, boolean requiresReferral, boolean isInsured) {
        Service service = new Service(generateId(), name, requiresReferral, isInsured);
        services.add(service);
        return service;
    }

    public List<Service> getServices() {
        return services;
    }

    private String generateId() {
        return "SV" + (services.size() + 1);
    }
}
