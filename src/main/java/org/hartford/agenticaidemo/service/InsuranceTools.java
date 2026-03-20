package org.hartford.agenticaidemo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class InsuranceTools {

    private final Map<String, String> policyStore = new HashMap<>();

    public InsuranceTools() {
        policyStore.put("POL-001", "Comprehensive Auto Insurance - Coverage up to $50,000 for collision, theft, and natural disasters. Status: ACTIVE.");
        policyStore.put("POL-002", "Term Life Insurance - $1M coverage, 20-year term. Beneficiary: Standard. Status: PENDING.");
        policyStore.put("POL-003", "Property Insurance - Full coverage for home and contents. Status: ACTIVE.");
    }

    public record PolicyRequest(String policyId) {}
    public record PremiumRequest(double basePrice, int age, int safetyScore) {}
    public record FAQRequest(String query) {}

    @Bean
    @Description("Lookup policy details and status by its policy ID (e.g., POL-001)")
    public Function<PolicyRequest, String> policy_lookup() {
        return request -> policyStore.getOrDefault(request.policyId().toUpperCase(), 
            "Policy ID " + request.policyId() + " not found in our records.");
    }

    @Bean
    @Description("Calculate the annual premium for an insurance policy based on base price, age of applicant, and driving record score (1-10, where 10 is safest)")
    public Function<PremiumRequest, Double> calculate_premium() {
        return request -> {
            double multiplier = (request.age() < 25) ? 1.5 : 1.0;
            double discount = (request.safetyScore() > 8) ? 0.9 : 1.0;
            return request.basePrice() * multiplier * discount;
        };
    }

    @Bean
    @Description("Get frequently asked questions about insurance policies, deductibles, and claims")
    public Function<FAQRequest, String> get_policy_faq() {
        return request -> {
            String query = request.query().toLowerCase();
            if (query.contains("deductible")) {
                return "A deductible is the amount you pay out-of-pocket for a claim before the insurance company starts to pay.";
            } else if (query.contains("claim")) {
                return "To file a claim, you can use our mobile app or call our 24/7 support line. Documentation of the incident will be needed.";
            } else {
                return "Please contact our support for more specific insurance questions.";
            }
        };
    }
}
