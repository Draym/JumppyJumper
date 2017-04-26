package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.camera.CameraController;
import com.andres_k.components.controllers.EMode;
import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageInputPlayer;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.RandomTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 10/07/2015.
 */
public final class GameObjectController {
    private List<GameObject> entities;
    private List<GameObject> players;
    private int winnerSlimes;
    public int bonusPoint;

    private GameObjectController() {
        this.entities = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    private static class SingletonHolder {
        private final static GameObjectController instance = new GameObjectController();
    }

    public static GameObjectController get() {
        return SingletonHolder.instance;
    }


    // INIT
    public void init() {
    }

    public void initWorld() throws SlickException {
        this.winnerSlimes = 0;
        this.bonusPoint = 0;
        this.entities.add(GameObjectFactory.create(EGameObject.MAP, ResourceManager.get().getBackgroundAnimator(EBackground.MAP_1), "Map_1:1", 1245, 415));
        this.entities.add(GameObjectFactory.create(EGameObject.MAP, ResourceManager.get().getBackgroundAnimator(EBackground.MAP_1), "Map_1:2", 3735, 415));
        this.entities.add(GameObjectFactory.create(EGameObject.COIN, ResourceManager.get().getGameAnimator(EGameObject.COIN), "coin:1", 800, 600));
        this.entities.add(GameObjectFactory.create(EGameObject.HEART, ResourceManager.get().getGameAnimator(EGameObject.HEART), "heart:1", 220, 640));
        this.entities.add(GameObjectFactory.create(EGameObject.GATE, ResourceManager.get().getGameAnimator(EGameObject.GATE), "gate:1", 900, 640));


        GameObject it1 = GameObjectFactory.create(EGameObject.FIRE_GUN, ResourceManager.get().getGameAnimator(EGameObject.FIRE_GUN), "fireGun:1", 1370, 685);
        GameObject it2 = GameObjectFactory.create(EGameObject.FIRE_GUN, ResourceManager.get().getGameAnimator(EGameObject.FIRE_GUN), "fireGun:2", 1450, 685);

        it1.getAnimatorController().setEyesDirection(EDirection.RIGHT);
        it1.getAnimatorController().setRotateAngle(-90);
        it2.getAnimatorController().setEyesDirection(EDirection.RIGHT);
        it2.getAnimatorController().setRotateAngle(-90);
        it2.getAnimatorController().forceNextFrame();
        this.entities.add(it1);
        this.entities.add(it2);
    }

    // FUNCTIONS

    public void enter() throws SlickException {
        this.initWorld();
    }

    public void leave() {
        this.players.forEach(GameObject::clear);
        this.players.clear();
        this.entities.forEach(GameObject::clear);
        this.entities.clear();
    }

    public void draw(Graphics g) throws SlickException {
        for (GameObject player : this.players) {
            player.draw(g);
        }
        for (GameObject object : this.entities) {
            object.draw(g);
        }
    }

    private void doMovement(GameObject item) {
        if (item.getType().isIn(EGameObject.ANIMATED))
            item.doMovement(this.checkCollision(item, ETaskType.MOVE));
    }

    public void update(boolean running) throws SlickException {
        if (running) {
            for (int i = 0; i < this.players.size(); ++i) {
                this.players.get(i).update();
                if (this.players.get(i).isNeedDelete()) {
                    this.thisPlayerIsDead((Player) this.players.get(i));
                    this.players.remove(i);
                    --i;
                } else {
                    this.doMovement(this.players.get(i));
                }
            }
            for (int i = 0; i < this.entities.size(); ++i) {
                this.entities.get(i).update();
                if (this.entities.get(i).isNeedDelete()) {
                    this.entities.remove(i);
                    --i;
                } else {
                    this.doMovement(this.entities.get(i));
                }
            }
            CameraController.get().followOwner(this.getPlayerById(CameraController.get().getIdOwner()));
        }
    }

    private void updateOwnerCameraPlayer() {
        float maxX = 0;
        int saveI = 0;

        for (int i = 0; i < this.players.size(); ++i) {
            if (maxX <= this.players.get(i).getPosX()) {
                maxX = this.players.get(i).getPosX();
                saveI = i;
            }
        }
        CameraController.get().setIdOwner(this.players.get(saveI).getId());
    }

    public void addWinner(String id) {
        this.winnerSlimes += 1;
    }

    // TASK

    public void taskForPlayer(String id, Object task) {
        GameObject player = this.getObjectById(id);

        if (player != null) {
            player.doTask(task);
        }
    }

    // EVENTS
    public void event(EInput event, EInput input) {
        if (input.getIndex() >= 0) {
            GameObject player = this.getPlayerById("players" + GlobalVariable.id_delimiter + String.valueOf(input.getIndex()));

            if (player != null) {
                NetworkController.get().sendMessage(player.getId(), new MessageInputPlayer(event, input));
                if (event == EInput.KEY_RELEASED) {
                    player.eventReleased(input);
                } else if (event == EInput.KEY_PRESSED) {
                    player.eventPressed(input);
                }
            }
        }
    }

    public void thisPlayerIsDead(Player player) {
        Console.write("\n A Slime is dead");
        if (player.getId().equals(CameraController.get().getIdOwner())) {
            this.updateOwnerCameraPlayer();
        }
    }

    public void changeGameState(boolean running) throws SlickException {
        for (GameObject object : this.players) {
            object.getAnimatorController().currentAnimation().setAutoUpdate(running);
            object.resetActions();
        }
        for (GameObject object : this.entities) {
            object.getAnimatorController().currentAnimation().setAutoUpdate(running);
            object.resetActions();
        }
    }

    // ADD

    public void createPlayers(List<EGameObject> playerNames) throws SlickException {
        Integer count;
        int startX;
        int slimeWidth = ResourceManager.get().getGameAnimator(EGameObject.SLIME).getCurrentContainer().getAnimation(0).getWidth();

        if (!playerNames.isEmpty()) {
            count = 1;
            startX = 150;
            for (EGameObject type : playerNames) {
                this.createPlayer(type, "player_slime:" + count, startX, 200, 600, 200, true);
                startX += slimeWidth - 10;
                ++count;
            }
        }
        this.updateOwnerCameraPlayer();
    }

    public void createPlayer(EGameObject type, String id, float startX, int boundX, float startY, int boundY, boolean ally) throws SlickException {
        GameObject player = null;
        float randomX = startX;
        float randomY = startY;
        boolean checked = false;

        while (player == null || this.checkCollision(player, ETaskType.STATIC).hasCollision()) {
            if (checked) {
                randomX = (float) RandomTools.getInt(boundX) + startX;
                randomY = (float) RandomTools.getInt(boundY) + startY;
            }
            player = GameObjectFactory.create(type, ResourceManager.get().getGameAnimator(type), id, randomX, randomY);
            player.doTask(new Tuple<>(ETaskType.SETTER, "teamOne", ally));
            checked = true;
        }
        Console.write("Create Player: " + player);
        /*
        if (ally) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_AlliedPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(players.id, 475, 85, EGuiElement.getEnum(players.getType().getValue(), EGuiElement.ICON.getValue()), false))));
        } else {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_EnemyPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(players.id, 475, 85, EGuiElement.getEnum(players.getType().getValue(), EGuiElement.ICON.getValue()), true))));
        }*/
        this.players.add(player);
    }

    public void createEntity(EGameObject type, String id, int boundX, int startX, int boundY, int startY) throws SlickException {
        GameObject object = null;

        while (object == null || this.checkCollision(object, ETaskType.STATIC).hasCollision()) {
            int randomX = RandomTools.getInt(boundX) + startX;
            int randomY = RandomTools.getInt(boundY) + startY;
            object = GameObjectFactory.create(type, ResourceManager.get().getGameAnimator(type), id, randomX, randomY);
            object.doTask(new Tuple<>(ETaskType.SETTER, "teamOne", false));
        }
        Console.write("Create Entity: " + object);
        if (object instanceof Player) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_EnemyPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(object.id, 475, 85, EGuiElement.getEnum(object.getType().getValue(), EGuiElement.ICON.getValue()), true))));
        }
        this.entities.add(object);
    }

    public void addEntity(GameObject entity) {
        this.entities.add(entity);
    }

    public void deleteEntity(String id) {
        Console.write("delete : " + id);
        for (int i = 0; i < this.entities.size(); ++i) {
            if (this.entities.get(i).getId().equals(id)) {
                Console.write("REMOVE");
                this.entities.remove(i);
                --i;
            }
        }
    }

    // COLLISION

    public CollisionResult checkCollision(GameObject current, ETaskType type) {
        CollisionResult result = new CollisionResult();

        if (current != null) {
            List<GameObject> items = this.getAllExpectHim(current.getId());
            Pair<Float, Float> newPos;

            if (type == ETaskType.STATIC) {
                newPos = new Pair<>(current.getPosX(), current.getPosY());
            } else {
                newPos = current.predictNextPosition();

                for (int i = 2; i < 6; ++i) {
                    Pair<Float, Float> interPos = new Pair<>(newPos.getV1() - (current.getMovement().calculatePushX() / i), newPos.getV2() - (current.getMovement().calculatePushY() / i));
                    result = current.checkCollision(items, interPos);
                    if (result.hasCollision()) {
                        break;
                    }
                }
            }
            if (!result.hasCollision()) {
                result = current.checkCollision(items, newPos);
            }
        }
        return result;
    }

    // GETTERS

    public List<GameObject> getPlayers() {
        return this.players;
    }

    public List<GameObject> getEntities() {
        return this.entities;
    }

    public List<GameObject> getAllExpectHim(String id) {
        List<GameObject> items = new ArrayList<>();
        items.addAll(this.players.stream().filter(object -> !id.contains(object.getId())).collect(Collectors.toList()));
        items.addAll(this.entities.stream().filter(object -> !id.contains(object.getId())).collect(Collectors.toList()));
        return items;
    }

    public GameObject getPlayerById(String id) {
        for (GameObject player : this.players) {
            if (player.getId().contains(id)) {
                return player;
            }
        }
        return null;
    }

    public GameObject getObjectById(String id) {
        for (GameObject player : this.players) {
            if (player.getId().contains(id)) {
                return player;
            }
        }
        for (GameObject entity : this.entities) {
            if (entity.getId().contains(id)) {
                return entity;
            }
        }
        return null;
    }

    public boolean isTheEndOfTheGame() {
        return (GameConfig.mode != EMode.SOLO && this.getNumberPlayers() == 0);
    }

    public int getNumberPlayers() {
        int nbPlayer = this.players.size();

        for (GameObject object : this.entities) {
            if (object instanceof Player) {
                nbPlayer += 1;
            }
        }
        return nbPlayer;
    }

    public int getWinnerSlimes() {
        return this.winnerSlimes;
    }
}
