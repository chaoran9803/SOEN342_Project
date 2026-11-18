package domain;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalCatalog {

    private List<HealthcareProfessional> professionals = new ArrayList<>();

    public HealthcareProfessional findProfessional(String professionalId) {
        for (HealthcareProfessional professional : professionals) {
            if (professional.getProfessionalId().equalsIgnoreCase(professionalId)) {
                return professional;
            }
        }
        return null;
    }

    public HealthcareProfessional findProfessionalByName(String name) {
        for (HealthcareProfessional professional : professionals) {
            if (professional.getName().equalsIgnoreCase(name)) {
                return professional;
            }
        }
        return null;
    }

    public HealthcareProfessional addProfessional(String name, String type) {
        HealthcareProfessional professional = new HealthcareProfessional(generateId(), name, type);
        professionals.add(professional);
        return professional;
    }

    public List<HealthcareProfessional> getProfessionals() {
        return professionals;
    }

    private String generateId() {
        return "HP" + (professionals.size() + 1);
    }
}
