
package org.mule.tooling.ui.contribution;

import javax.annotation.Generated;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 * 
 */
@Generated(value = "Mule DevKit Version 3.5.0-SNAPSHOT", date = "2014-02-14T11:47:11-06:00", comments = "Build UNKNOWN_BUILDNUMBER")
public class GoogleSpreadsheetsActivator
    extends AbstractUIPlugin
{

    public final static String PLUGIN_ID = "org.mule.tooling.ui.contribution.google-spreadsheets";
    private static org.mule.tooling.ui.contribution.GoogleSpreadsheetsActivator plugin;

    public void start(BundleContext context)
        throws Exception
    {
        super.start(context);
        plugin = this;
    }

    public void stop(BundleContext context)
        throws Exception
    {
        plugin = null;
        super.stop(context);
    }

    public static org.mule.tooling.ui.contribution.GoogleSpreadsheetsActivator getDefault() {
        return plugin;
    }

}
