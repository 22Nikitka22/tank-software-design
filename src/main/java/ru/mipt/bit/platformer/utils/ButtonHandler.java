package ru.mipt.bit.platformer.utils;

import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ButtonHandler {
    List<ButtonAction> buttonActions = new ArrayList<>();

    public void add(Collection<Integer> button, Runnable runnable) {
        buttonActions.add(new ButtonAction(button, runnable));
    }

    public void check(Input input) {
        for (var buttonAction : buttonActions) {
            if (buttonAction.getButton().stream().anyMatch(input::isKeyPressed))
                buttonAction.getAction().run();
        }
    }

    private static class ButtonAction {
        private final Collection<Integer> button;
        private final Runnable action;

        public ButtonAction(Collection<Integer> button, Runnable runnable) {
            this.button = button;
            this.action = runnable;
        }

        public Collection<Integer> getButton() { return button; }

        public Runnable getAction() { return action; }
    }
}
