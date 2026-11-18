package domain;

public abstract class Professional {

    private String professionalId;
    private String name;
    private String licenseNumber;

    public Professional(String professionalId, String name, String licenseNumber) {
        this.professionalId = professionalId;
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}
