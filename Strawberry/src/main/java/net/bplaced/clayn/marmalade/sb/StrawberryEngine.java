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

import java.util.HashMap;
import java.util.Map;
import net.bplaced.clayn.marmalade.core.script.EngineFeature;
import net.bplaced.clayn.marmalade.core.script.ExecutionModule;
import net.bplaced.clayn.marmalade.core.script.ScriptExecutionEngine;
import net.bplaced.clayn.marmalade.core.util.Tuple;

/**
 *
 * @author Clayn <clayn_osmato@gmx.de>
 */
public class StrawberryEngine implements ScriptExecutionEngine
{

    private final Map<String, String> variables = new HashMap<>();

    private final Map<Tuple<String, String>, ExecutionModule> registeredModules = new HashMap<>();

    @Override
    public boolean isSupported(EngineFeature feature)
    {
        return true;
    }

    @Override
    public void register(ExecutionModule module)
    {
        if (module.getJam().equals("strawberry"))
        {
            return;
        }
        registeredModules.put(Tuple.create(module.getJam(), module.getName()),
                module);
    }

    @Override
    public void setVariable(String name, String val)
    {
        variables.put(name, val);
    }

}
