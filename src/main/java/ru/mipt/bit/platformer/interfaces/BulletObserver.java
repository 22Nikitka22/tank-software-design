package ru.mipt.bit.platformer.interfaces;

import ru.mipt.bit.platformer.objects.models.BulletModel;

public interface BulletObserver {
    void bulletAppeared(BulletModel bullet);
    void bulletDestroyed(BulletModel bullet);
}
