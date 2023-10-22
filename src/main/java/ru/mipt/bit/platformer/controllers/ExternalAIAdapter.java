package ru.mipt.bit.platformer.controllers;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.Action;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Actor;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.logic.objects.GameObject;
import ru.mipt.bit.platformer.logic.objects.Obstacle;
import ru.mipt.bit.platformer.logic.objects.Tank;

import java.util.*;

public class ExternalAIAdapter {
    private final AI ai;
    private GameState gameState = new GameState.GameStateBuilder().build();
    private final HashMap<Actor, GameObject> actorToGameObject = new HashMap<>();
    private final List<org.awesome.ai.state.immovable.Obstacle> obstacles = new ArrayList<>();
    private Player player;
    private final List<Bot> bots = new ArrayList<>();
    private final int tilesWidth;
    private final int tilesHeight;

    public ExternalAIAdapter(int tilesWidth, int tilesHeight, AI ai) {
        this.tilesHeight = tilesHeight;
        this.tilesWidth = tilesWidth;
        this.ai = ai;
    }

    public void add(GameObject gameObject) {
        //if it is player tank
        if (gameObject instanceof Tank && ((Tank)gameObject).isPlayer()) {
            player = new Player.PlayerBuilder().source(gameObject).build();
            actorToGameObject.put(player, gameObject);
        }
        //if it is bot tank
        if (gameObject instanceof Tank && !((Tank)gameObject).isPlayer()) {
            Bot bot = new Bot.BotBuilder().source(gameObject).build();
            actorToGameObject.put(bot, gameObject);
            bots.add(bot);
        }
        //if it is obstacle
        if (gameObject instanceof Obstacle) {
            org.awesome.ai.state.immovable.Obstacle obstacle =
                    new org.awesome.ai.state.immovable.Obstacle(((Obstacle) gameObject).getCoordinates().x,
                            ((Obstacle) gameObject).getCoordinates().y);
            obstacles.add(obstacle);
        }
        gameState = new GameState.GameStateBuilder().bots(bots).player(player)
                        .obstacles(obstacles).levelWidth(tilesWidth).levelHeight(tilesHeight).build();
    }

    public Map.Entry<GameObject, ArrayList<ru.mipt.bit.platformer.actions.Action>> getActions(GameObject gameObject) {
        Map.Entry<GameObject, ArrayList<ru.mipt.bit.platformer.actions.Action>> gameToObjectActions =
                new AbstractMap.SimpleEntry<>(gameObject, new ArrayList<>());

        List<Recommendation> recommendations = ai.recommend(gameState);

        for (Recommendation recommendation : recommendations) {
            if (actorToGameObject.get(recommendation.getActor()) == gameObject) {
                gameToObjectActions.getValue().add(parseAction(recommendation.getAction()));
            }
        }
        return gameToObjectActions;
    }

    //no using in java((
    private ru.mipt.bit.platformer.actions.Action parseAction(
            org.awesome.ai.Action preParsedAction) {

        ru.mipt.bit.platformer.actions.Action parsedAction;
        switch (preParsedAction) {
            case Shoot: {
                parsedAction = new ShootAction();
                break;
            }
            case MoveSouth: {
                parsedAction = MoveAction.DOWN;
                break;
            }
            case MoveEast: {
                parsedAction = MoveAction.RIGHT;
                break;
            }
            case MoveWest: {
                parsedAction = MoveAction.LEFT;
                break;
            }
            case MoveNorth: {
                parsedAction = MoveAction.UP;
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown AI action");
        }
        return parsedAction;
    }
}
