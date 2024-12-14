package ru.mipt.bit.platformer.interfaces;

public interface Observer {
    void objectAppeared(MovingModel model, String name);
    void objectDestroyed(MovingModel model, String name);
}
