package com.andres_k.custom.component.resourceComponents.resources.data;

import com.andres_k.gameToolsLib.components.eventComponents.input.InputData;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables.SliderElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.ElementWithTitle;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.PaginatedList;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.specific.AlterableInputControlElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.custom.component.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.data.DataManager;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import com.andres_k.gameToolsLib.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.io.IOException;

/**
 * Created by andres_k on 16/02/2016.
 */
public class InterfaceElementData extends DataManager {

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException, IOException, FontFormatException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initVolumesContent")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initControlsContent")));
    }

    @Override
    public String success() {
        return "> Interface complete";
    }

    public void initVolumesContent() throws NoSuchMethodException, SlickException, JSONException {
        ComplexElement volumes = new ComplexElement(ELocation.GUI_ELEMENT_Volumes.getId(), new ColorRect(new Rectangle(30, 70, 520, 300)), true);
        volumes.addItem(new TextElement(new StringTimer("Volumes"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, 30, 20, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new TextElement(new StringTimer("Music :"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 0, 75, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new SliderElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(180, 70, 310, 37)), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER_VALUE), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_SLIDER), true), new Pair<>(ELocation.MUSIC_CONTROLLER, ETaskType.CHANGE_VOLUME), MusicController.get().getVolume(), true));
        volumes.addItem(new TextElement(new StringTimer("Sound Effect :"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 0, 145, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new SliderElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(180, 140, 310, 37)), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER_VALUE), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_SLIDER), true), new Pair<>(ELocation.SOUND_CONTROLLER, ETaskType.CHANGE_VOLUME), MusicController.get().getVolume(), true));
        GuiElementsManager.get().add(volumes.getId(), volumes);
    }

    public void initControlsContent() throws NoSuchMethodException, SlickException, JSONException {
        PaginatedList playerControls = new PaginatedList(ELocation.GUI_ELEMENT_PlayerControls.getId(), new ColorRect(new Rectangle(5, 50, 570, 390)), new ColorRect(new Rectangle(20, 60, 540, 310)), EGuiElement.TAB_STATUS, 10, 0, 20, 0, true, true);
        playerControls.createList(ElementFactory.createText("Character", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 0, 0), 0, 20);
        InputData.getAvailableInput().entrySet().forEach(entry -> {
            TextElement title = new TextElement(new StringTimer(StringTools.formatIt(entry.getKey().getContainer().getValue(), 20, ":", 10, "")), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 16, true);
            TextElement content = new AlterableInputControlElement(entry.getKey().getValue(), new StringTimer(entry.getValue()), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 200, 0, Element.PositionInBody.MIDDLE_MID, true);
            playerControls.addItem("Character", new ElementWithTitle(new ColorRect(new Rectangle(0, 0, 0, 0)), title, content, true));
        });
        GuiElementsManager.get().add(playerControls.getId(), playerControls);
    }
}
