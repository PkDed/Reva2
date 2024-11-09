package app.revanced.extension.youtube.patches.announcements.requests;

import app.revanced.extension.youtube.requests.Requester;
import app.revanced.extension.youtube.requests.Route;

import java.io.IOException;
import java.net.HttpURLConnection;

import static app.revanced.extension.youtube.requests.Route.Method.GET;

public class AnnouncementsRoutes {
    public static final Route GET_LATEST_ANNOUNCEMENTS = new Route(GET, "/announcements/latest?tag=youtube&language={language}");
    public static final Route GET_LATEST_ANNOUNCEMENT_IDS = new Route(GET, "/announcements/latest/id?tag=youtube");
    private static final String ANNOUNCEMENTS_PROVIDER = "https://api.revanced.app/v4";

    private AnnouncementsRoutes() {
    }

    public static HttpURLConnection getAnnouncementsConnectionFromRoute(Route route, String... params) throws IOException {
        return Requester.getConnectionFromRoute(ANNOUNCEMENTS_PROVIDER, route, params);
    }
}