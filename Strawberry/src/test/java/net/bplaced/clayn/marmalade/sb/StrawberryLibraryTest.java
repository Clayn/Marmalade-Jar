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

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.bplaced.clayn.marmalade.core.Game;
import net.bplaced.clayn.marmalade.core.script.NotYetConfiguredException;
import net.bplaced.clayn.marmalade.jar.err.GameAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author Clayn <clayn_osmato@gmx.de>
 */
public class StrawberryLibraryTest
{

    @TempDir
    File testLibraryDir;

    private StrawberryLibrary library;

    @BeforeEach
    public void prepareTest()
    {
        library = new StrawberryLibrary();
        try
        {
            library.getGames();
            Assertions.fail();
        } catch (NotYetConfiguredException nyce)
        {

        }
        Map<String, Object> config = new HashMap<>();
        config.put("sb.library.path", testLibraryDir);
        library.configure(config);
        Assertions.assertTrue(library.getGames().isEmpty());
    }

    @Test
    public void testCreateGameMultiple() {
        Game g = library.createGame("Some Game");
        Assertions.assertNotNull(g);
        Assertions.assertEquals("Some Game", g.getName());
        Assertions.assertThrows(GameAlreadyExistsException.class, new Executable()
        {
            @Override
            public void execute() throws Throwable
            {
                library.createGame("Some Game");
            }
        });
    }
    @Test
    public void testCreateGame()
    {
        Game g = library.createGame("Some Game");
        Assertions.assertNotNull(g);
        Assertions.assertEquals("Some Game", g.getName());
        Assertions.assertNotEquals(0, testLibraryDir.listFiles().length);
    }
}
