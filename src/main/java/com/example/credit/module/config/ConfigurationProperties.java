package com.example.credit.module.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class ConfigurationProperties {
    @Value("${loan.allowed.installments:3,6,9}")
    private String allowedInstallments;

    @Value("${loan.interest.rate.min:0.1}")
    private double interestRateMin;

    @Value("${loan.interest.rate.max:0.5}")
    private double interestRateMax;

    @Value("${loan.payment.reward.multiplier:0.001}")
    private double rewardMultiplier;

    @Value("${loan.payment.penalty.multiplier:0.001}")
    private double penaltyMultiplier;

    public List<Integer> getAllowedInstallments() {
        return Stream.of(allowedInstallments.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public double getInterestRateMin() {
        return interestRateMin;
    }

    public double getInterestRateMax() {
        return interestRateMax;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public double getPenaltyMultiplier() {
        return penaltyMultiplier;
    }
}
