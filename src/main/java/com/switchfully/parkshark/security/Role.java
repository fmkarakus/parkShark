package com.switchfully.parkshark.security;

import com.google.common.collect.Lists;

import java.util.List;

import static com.switchfully.parkshark.security.Feature.*;

public enum Role {
    MANAGER("manager",
            CREATE_DIVISION,
            GET_ALL_DIVISIONS,
            GET_A_DIVISION_BY_ID,
            CREATE_PARKING_LOT,
            VIEW_ALL_PARKINGLOTS,
            CREATE_SUBDIVISION,
            GET_ALL_MEMBERS),
    MEMBER("member");

    private final String label;
    private final List<Feature> featureList;

    Role(String label, Feature... featureList) {
        this.label = label;
        this.featureList = Lists.newArrayList(featureList);
    }

    public List<Feature> getFeatures() {
        return featureList;
    }

    public String getLabel() {
        return label;
    }
}
