package fr.bouyguestelecom.innovationlab.miamiwear.utils;

/**
 * Created by InnovationLab on 20/08/2014 for Miami Wear
 */
public class Constants {
    // URL route
    public static final String HTTP = "http://";
    public static final String CLOUD_API = "https://dev.bouyguestelecom.fr"; // Cloud API
    public static final String BOX_API = ":8080/api.bbox.lan/v0"; // Box API
    public static final String FLEX = "http://openbbox.flex.bouyguesbox.fr:81/v0/media";
    public static final String MEDIA = "/media";
    public static final String EPG_LIVE = "/epg/live";
    public static final String TOKEN = "/security/token";
    public static final String SESSION_ID = "/security/sessionId";

    // Headers
    public static final String HEADER_TOKEN = "x-token";
    public static final String HEADER_SESSION_ID = "x-sessionid";

    public static final String START_WEAR_APPLICATION = "/start";
    public static final String INIT_INFOS = "/init";
    public static final String INIT_INDEX = "index";
    public static final String INIT_COUNT = "count";
    public static final String IS_ALIVE = "/alive/";

    public static final String END_WEAR_APPLICATION = "/end";
    public static final String ASK_IMAGES = "/images/";
    public static final String ACTION_CHANGE_CHANNEL = "/zap/";

    public static final String EPG_DATA = "/data";
    public static final String EPG_ID = "epg_id";
    public static final String EPG_NAME = "epg_name";
    public static final String EPG_DESC = "epg_desc";
    public static final String EPG_STARTTIME = "epg_starttime";
    public static final String EPG_ENDTIME = "epg_endtime";
    public static final String EPG_POSITION = "epg_position";
    public static final String EPG_CHANNEL_NUMBER = "epg_channel_number";
    public static final String EPG_TIMESTAMP = "timestamp";
    public static final String EPG_COUNT = "count";
    public static final String EPG_IMAGE = "image";
    public static final String EPG_LOGO = "logo";

    public static final String REMOTE_STARTED = "remote";
    public static final String REMOTE_PPLUS = "p_plus";
    public static final String REMOTE_PMOINS = "p_moins";
    public static final String REMOTE_VPLUS = "v_plus";
    public static final String REMOTE_VMOINS = "v_moins";
    public static final String REMOTE_WOL = "WOL";
    public static final String REMOTE_WOL_OK = "wol_ok";
    public static final String REMOTE_MUTE = "mute";
    public static final String REMOTE_UP = "up";
    public static final String REMOTE_DOWN = "down";
    public static final String REMOTE_LEFT = "left";
    public static final String REMOTE_RIGHT = "right";
    public static final String REMOTE_OK = "ok";
    public static final String REMOTE_BACK = "back";
    public static final String REMOTE_HOME = "home";
}
