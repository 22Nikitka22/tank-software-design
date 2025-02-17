package ru.mipt.bit.platformer.interfaces;

public interface PlayingModel {
    float getHealth();
    void shoot();
    void setBulletObserver(BulletObserver bulletObserver);
    void setTankObserver(TankObserver tankObserver);
}
