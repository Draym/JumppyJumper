package com.andres_k.custom.component.graphicComponents.userInterface.elementGUI;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;

/**
 * Created by andres_k on 05/02/2016.
 */
public enum EGuiElement {
    /* Admin */
    NULL("NULL"),
    BUTTON("BUTTON"),
    CONTAINER("CONTAINER"),
    COMPONENT("COMPONENT"),
    CARDS("CARDS"),
    AVATAR("AVATAR"),
    ICON("ICON"),

    // container
    PANEL1("PANEL1", CONTAINER.getValue()),
    PANEL2("PANEL2", CONTAINER.getValue()),
    PANEL3("PANEL3", CONTAINER.getValue()),
    PANEL4("PANEL4", CONTAINER.getValue()),

    // button
    BUTTON_PLAY_SOLO("BUTTON_PLAY_SOLO", BUTTON.getValue()),
    BUTTON_PLAY_VERSUS("BUTTON_PLAY_VERSUS", BUTTON.getValue()),
    BUTTON_PLAY_MULTI("BUTTON_PLAY_MULTI", BUTTON.getValue()),
    BUTTON_SETTING("BUTTON_SETTING", BUTTON.getValue()),
    BUTTON_CONTROLS("BUTTON_CONTROLS", BUTTON.getValue()),
    BUTTON_SLIDER("BUTTON_SLIDER", BUTTON.getValue()),
    BUTTON_CLOSE("BUTTON_CLOSE", BUTTON.getValue()),
    BUTTON_EXIT("BUTTON_EXIT", BUTTON.getValue()),
    BUTTON_RESUME("BUTTON_RESUME", BUTTON.getValue()),
    BUTTON_COMBO("BUTTON_COMBO", BUTTON.getValue()),
    BUTTON_LOCK("BUTTON_LOCK", BUTTON.getValue()),

    // component
    TAB_STATUS("TAB_STATUS", COMPONENT.getValue()),
    SLIDER("SLIDER", COMPONENT.getValue()),
    SLIDER_VALUE("SLIDER_VALUE", COMPONENT.getValue()),
    LOAD_BAR("LOAD_BAR", COMPONENT.getValue()),
    LOADING_ANIM("LOADING_ANIM", COMPONENT.getValue()),
    DISABLED("DISABLED", COMPONENT.getValue()),
    HEALTH_BAR("HEALTH_BAR", COMPONENT.getValue()),
    KI_BAR("KI_BAR", COMPONENT.getValue()),
    ENERGY_BAR("ENERGY_BAR", COMPONENT.getValue()),
    STATE_PLAYER("STATE_PLAYER", COMPONENT.getValue()),
    START_LOGO("START_LOGO", COMPONENT.getValue()),

    // GUI_CARDS
    CARDS_ALL("CARDS_ALL", CARDS.getValue()),

    // GUI_AVATAR
    AVATAR_SLIME(EGameObject.SLIME.getValue(), AVATAR.getValue()),
    AVATAR_BORDER("AVATAR_BORDER", AVATAR.getValue()),

    // GUI_ICON
    ICON_SLIME(EGameObject.SLIME.getValue(), ICON.getValue());

    private final String value;
    private final String type;

    EGuiElement(String value) {
        this.value = value;
        this.type = value;
    }

    EGuiElement(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return this.type;
    }

    public static EGuiElement getEnum(String value, String type) {
        EGuiElement[] enums = EGuiElement.values();
        for (EGuiElement item : enums) {
            if (item.getValue().equals(value) && item.getType().equals(type))
                return item;
        }
        return NULL;
    }
}
