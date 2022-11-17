package com.switchfully.parkshark.domain.parkinglot;

import java.util.NoSuchElementException;

public enum Category {
    UNDERGROUND,
    ABOVE_GROUND;

    public static Category findCategoryByName(String name){
        for (Category category: Category.values()){
            if (category.toString().equals(name.toUpperCase())){
                return category;
            }
        }
        throw new NoSuchElementException("Category does not exist");
    }
}

