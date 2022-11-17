package com.switchfully.parkshark.domain.member;

import java.util.NoSuchElementException;

public enum MembershipLevel {
    BRONZE(0.0, 0.0, 4),
    SILVER(10.0, 0.2, 6),
    GOLD(40.0, 0.3, 24);

    private final double monthlyCost;
    private final double reduction;
    private final int maxAllocationTime;

    MembershipLevel(double monthlyCost, double reduction, int maxAllocationTime) {
        this.monthlyCost = monthlyCost;
        this.reduction = reduction;
        this.maxAllocationTime = maxAllocationTime;
    }

    public static MembershipLevel findMembershipLevelByName(String membershipLevelName) {
        for (MembershipLevel level : MembershipLevel.values()) {
            if (level.toString().equals(membershipLevelName)) {
                return level;
            }
        }
        throw new NoSuchElementException(String.format("Membership level with name %s does not exist", membershipLevelName));
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public double getReduction() {
        return reduction;
    }

    public int getMaxAllocationTime() {
        return maxAllocationTime;
    }
}
