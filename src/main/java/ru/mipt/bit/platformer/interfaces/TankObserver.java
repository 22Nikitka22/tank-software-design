package ru.mipt.bit.platformer.interfaces;

import ru.mipt.bit.platformer.objects.models.TankModel;

public interface TankObserver {
    void tankDestroyed(TankModel tank);
}
