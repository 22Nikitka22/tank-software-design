package ru.mipt.bit.platformer.objects.models;

public class HealthBarModel {

    private boolean visible;

    public HealthBarModel(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    public void switchVisible() {
        visible = !visible;
    }
}
