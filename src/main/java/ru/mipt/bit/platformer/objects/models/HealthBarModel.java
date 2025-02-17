package ru.mipt.bit.platformer.objects.models;

public class HealthBarModel {

    private static boolean visible = true;

    public static boolean getVisible() {
        return visible;
    }

    public static void switchVisible() {
        visible = !visible;
    }
}
