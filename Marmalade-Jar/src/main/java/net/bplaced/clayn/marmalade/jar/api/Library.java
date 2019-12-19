package net.bplaced.clayn.marmalade.jar.api;

import java.util.Set;
import net.bplaced.clayn.marmalade.core.Game;

/**
 *
 * @author Clayn <clayn_osmato@gmx.de>
 * @since 0.1
 */
public interface Library extends Jam
{

    public Game createGame(String name);

    public Set<Game> getGames();
}
