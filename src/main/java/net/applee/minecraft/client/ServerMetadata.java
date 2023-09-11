package net.applee.minecraft.client;

import net.applee.minecraft.utils.string.Text;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ServerMetadata {

    public int maxPlayers = -1;
    public int currentOnline = -1;
    public int versionProtocol = -1;

    public boolean previewsChat = false;
    public boolean enforcesSecureChat = false;
    public String brand;
    public Text motd;
    public Text description;
    public String versionName;
    public BufferedImage favicon;
    public BufferedImage icon;
    public List<JSONObject> players = new ArrayList<>();



}
