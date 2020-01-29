/*
 * The MIT License
 *
 * Copyright 2020 Clayn <clayn_osmato@gmx.de>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.bplaced.clayn.marmalade.sb;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bplaced.clayn.marmalade.core.Game;
import net.bplaced.clayn.marmalade.core.script.ModuleParameter;
import net.bplaced.clayn.marmalade.core.script.NotYetConfiguredException;
import net.bplaced.clayn.marmalade.jar.api.Library;
import net.bplaced.clayn.marmalade.jar.err.GameAlreadyExistsException;

/**
 *
 * @author Clayn <clayn_osmato@gmx.de>
 */
public class StrawberryLibrary implements Library
{

    private static final List<ModuleParameter> PARAMETER_LIST = new ArrayList<>();
    private File storingDirectory;

    static
    {
       
    }

    private void checkDirectory()
    {
        if (storingDirectory == null)
        {
            throw new NotYetConfiguredException(
                    "No directory was configured for the library");
        }
        if (!storingDirectory.exists())
        {
            if (!storingDirectory.mkdirs())
            {
                throw new NotYetConfiguredException(
                        "Failed to create the library directory '" + storingDirectory.getAbsolutePath() + "'");
            }
        }
    }

   

    private Game loadGame(String name)
    {
        return loadGame(new File(storingDirectory, name + ".sb"));
    }

    private Game loadGame(File f)
    {
        if (!f.exists())
        {
            return null;
        }
        StringBuilder builder = new StringBuilder((int) f.length());
        try
        {
            Files.readAllLines(f.toPath())
                    .forEach(builder::append);
        } catch (IOException ex)
        {
            Logger.getLogger(StrawberryLibrary.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return new Gson().fromJson(builder.toString(), Game.class);
    }

    @Override
    public Set<Game> getGames()
    {
        checkDirectory();
        Set<Game> games = new HashSet<>();
        File[] files = storingDirectory.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return pathname.isFile() && pathname.getName().endsWith(".sb");
            }
        });
        if (files != null)
        {
            for (File f : files)
            {
                games.add(loadGame(f));
            }
        }
        return games;
    }

    @Override
    public List<ModuleParameter> getConfigurationParameters()
    {
        return Collections.unmodifiableList(PARAMETER_LIST);
    }

    @Override
    public void configure(Map<String, Object> config)
    {
        Object obj = config.get("sb.library.path");
        File f = null;
        if (obj instanceof File)
        {
            f = (File) obj;
            if (f.isFile())
            {
                f = f.getParentFile();
            }
        } else if (obj instanceof String)
        {
            f = new File(obj.toString());
        }
        if (f != null)
        {
            storingDirectory = f;
        }
    }

    @Override
    public String getName()
    {
        return "library";
    }

    @Override
    public String getJam()
    {
        return "strawberry";
    }

    @Override
    public Game createGame(Game game)
    {
    try
        {
            checkDirectory();
            Game exist = loadGame(game.getName());
            if (exist != null)
            {
                throw new GameAlreadyExistsException(
                        "Game '" + game.getName() + "' was already created in this library");
            }
           
            File gameFile = new File(storingDirectory, game.getName() + ".sb");
            gameFile.createNewFile();
            try (FileWriter writer = new FileWriter(gameFile))
            {
                writer.write(new Gson().toJson(game));
                writer.flush();
            }
            return game;
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }    
    }

}
