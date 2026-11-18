package domain;

import java.util.ArrayList;
import java.util.List;

public class ReferralCatalog {

    private List<Referral> referrals = new ArrayList<>();

    public Referral createReferral(Patient patient, Specialty specialty, String clinicalReason, String priority) {
        Referral referral = new Referral(generateId(), patient, specialty, clinicalReason, priority);
        referrals.add(referral);
        return referral;
    }

    public boolean hasValidReferral(Patient patient, HealthcareProfessional professional) {
        for (Referral referral : referrals) {
            if (referral.isValidFor(patient, professional)) {
                return true;
            }
        }
        return false;
    }

    private String generateId() {
        return "R" + (referrals.size() + 1);
    }
}
