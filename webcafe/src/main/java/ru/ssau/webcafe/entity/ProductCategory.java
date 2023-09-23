package ru.ssau.webcafe.entity;

import lombok.Getter;

public enum ProductCategory {
    COFFEE("Кофе"),
    DESSERTS("Десерты"),
    SALADS("Салаты"),
    BREAKFAST("Завтраки"),
    HOTDISH("Горячее"),
    SIDE_DISHES("Гарниры"),
    BRUSCHETTA("Брускеты"),
    PASTA("Паста"),
    PIZZA("Пицца"),
    SNACKS("Закуски"),
    SOUPS("Супы"),
    BREAD("Хлеб"),
    SAUCES("Соусы"),
    ICE_CREAM("Мороженное"),
    TEA_DRINKS("Чайные напитки"),
    LEMONADES("Лимонады"),
    MILKSHAKES("Молочные коктейли"),
    JUICES("Соки"),
    STEWED_FRUIT("Компот"),
    WATER("Вода")
    ;

    @Getter private final String category;

    ProductCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "%s [%s]".formatted(name(), category);
    }
}
