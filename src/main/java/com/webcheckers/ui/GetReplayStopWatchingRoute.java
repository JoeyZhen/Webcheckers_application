package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.GetHomeRoute.NEW_PLAYER_ATTR;
import static com.webcheckers.ui.PostSigninRoute.MESSAGE_ATTR;

/**
 * The UI controller to GET the home page after stopping the replay
 *
 * @author <a href='mailto:nah6563@rit.edu'>Nathan Harding</a>
 * @author <a href='mailto:chb9645@rit.edu'>Christian Brady</a>
 * @author <a href='mailto:jxe5374@rit.edu'>Joey Zhen</a>
 * @author <a href='mailto:jtp8167@rit.edu'>Jonathan Pyc</a>
 * @author <a href='mailto:sao9945@rit.edu'>Ademide Osusina </a>
 */
    public class GetReplayStopWatchingRoute implements Route {


    public GetReplayStopWatchingRoute() {
    }

    @Override
    public String handle(Request request, Response response) {
        Gson gson = new Gson();
        final Session Session = request.session();
        Player player = Session.attribute(GetHomeRoute.PLAYER_KEY);
        player.setGameID(0);
        player.setTurn(0);
        response.redirect(WebServer.HOME_URL);
        return gson.toJson(new Message(Message.INFO, "Replay Ended"));
    }
}
