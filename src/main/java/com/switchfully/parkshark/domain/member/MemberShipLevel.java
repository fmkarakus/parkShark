package com.switchfully.parkshark.domain.member;

public enum MemberShipLevel {
    BRONZE(0.0, 0.0, 4),
    SILVER(10.0, 0.2, 6),
    GOLD(40.0, 0.3, 24);

    private final double monthlyCost;
    private final double reduction;
    private final int maxAllocationTime;

    MemberShipLevel(double monthlyCost, double reduction, int maxAllocationTime) {
        this.monthlyCost = monthlyCost;
        this.reduction = reduction;
        this.maxAllocationTime = maxAllocationTime;
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
