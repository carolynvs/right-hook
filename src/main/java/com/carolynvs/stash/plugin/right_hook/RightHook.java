package com.carolynvs.stash.plugin.right_hook;

import com.atlassian.stash.hook.HookResponse;
import com.atlassian.stash.hook.repository.PreReceiveRepositoryHook;
import com.atlassian.stash.hook.repository.RepositoryHookContext;
import com.atlassian.stash.repository.RefChange;
import org.json.JSONObject;

import java.net.URL;
import java.util.Collection;
import java.util.Scanner;

public class RightHook implements PreReceiveRepositoryHook
{
    @Override
    public boolean onReceive(RepositoryHookContext context, Collection<RefChange> refChanges, HookResponse hookResponse)
    {
        String quote = getQuote();
        if(quote != null)
        {
            hookResponse.out().println(quote);
        }
        return true;
    }

    private String getQuote()
    {
        try
        {
            URL url = new URL("http://api.icndb.com/jokes/random");
            Scanner scanner = new Scanner(url.openStream());
            String response = scanner.useDelimiter("\\Z").next();
            JSONObject json = new JSONObject(response);

            return json.getJSONObject("value").getString("joke");
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}