package net.bplaced.clayn.marmalade.jar.api;

import java.util.Set;
import net.bplaced.clayn.marmalade.core.Game;
import net.bplaced.clayn.marmalade.core.script.Module;

/**
 *
 * @author Clayn <clayn_osmato@gmx.de>
 * @since 0.1
 */
public interface Library extends Module
{

    public default Game createGame(String name) {
        Game g=new Game();
        g.setName(name);
        return createGame(g);
    }
    
    public Game createGame(Game game);

    public Set<Game> getGames();

}
