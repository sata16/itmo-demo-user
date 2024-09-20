package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
        WHITE("Белый"),
        BLACK("Черный"),
        BLUE("Голубой"),
        DARK_BLUE("Синий"),
        GREEN("Зеленый");

        private final String description;
    }

