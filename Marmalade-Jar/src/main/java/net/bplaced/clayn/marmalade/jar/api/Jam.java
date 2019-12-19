package net.bplaced.clayn.marmalade.jar.api;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author Clayn <clayn_osmato@gmx.de>
 * @since 0.1
 */
public interface Jam
{

    public Map<String, Class<?>> getConfigurationOptions();

    public Map<String, Object> getConfiguration();

    public default Map<String, Object> getDefaultConfiguration()
    {
        return Collections.emptyMap();
    }

    public String getName();

    public void configure(Map<String, Object> config);
}
