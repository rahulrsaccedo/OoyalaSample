package demo.ooyala.com.demoooyalaplayer;

/**
 * Created by Rahul R S on 09-04-2015.
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.OoyalaPlayerLayout;
import com.ooyala.android.PlayerDomain;
import com.ooyala.android.freewheelsdk.OoyalaFreewheelManager;
import com.ooyala.android.ui.OptimizedOoyalaPlayerLayoutController;


/**
 * This activity illustrates how to use Freewheel while manually configuring Freewheel settings
 *
 * Supported parameters for Freewheel Configuration:
 * - fw_android_mrm_network_id
 * - fw_android_ad_server
 * - fw_android_player_profile
 * - FRMSegment
 * - fw_android_site_section_id
 * - fw_android_video_asset_id
 */
public class SamplePlayerActivity extends Activity implements Observer {
    public final static String getName() {
        return "Preconfigured Freewheel Player";
    }
    final String TAG = this.getClass().toString();

    String EMBED = null;
    final String PCODE  = "R3ZHExOjHcfMbqoMxpYBE7PbDEyB";
    final String DOMAIN = "http://www.ooyala.com";

    protected OptimizedOoyalaPlayerLayoutController playerLayoutController;
    protected OoyalaPlayer player;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getName());
        setContentView(R.layout.activity_main);

        EMBED = "NwNnRiczqVVb9EtAZPEf-MzHK9iFlE4i";

        //Initialize the player
        OoyalaPlayerLayout playerLayout = (OoyalaPlayerLayout) findViewById(R.id.ooyalaPlayer);
        player = new OoyalaPlayer(PCODE, new PlayerDomain(DOMAIN));
        playerLayoutController = new OptimizedOoyalaPlayerLayoutController(playerLayout, player);
        player.addObserver(this);

        /** DITA_START:<ph id="freewheel_custom"> **/
        OoyalaFreewheelManager fwManager = new OoyalaFreewheelManager(this, playerLayoutController);

        Map<String, String> freewheelParameters = new HashMap<String, String>();
        freewheelParameters.put("fw_android_mrm_network_id",  "54f537d9e4b08c04f33eb1fa");
        freewheelParameters.put("fw_android_ad_server", "5d494.v.fwmrm.net");
        freewheelParameters.put("fw_android_player_profile",  "54f537d9e4b08c04f33eb1fa");
        freewheelParameters.put("FRMSegment",  "channel=TEST;subchannel=TEST;section=TEST;mode=online;player=ooyala;beta=n");
        freewheelParameters.put("fw_android_site_section_id", "dtv_androidphone");
        freewheelParameters.put("fw_android_video_asset_id",  EMBED);

        fwManager.overrideFreewheelParameters(freewheelParameters);
        /** DITA_END:</ph> **/

        if (player.setEmbedCode(EMBED)) {
            player.play();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Player Activity Stopped");
        if (player != null) {
            player.suspend();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Player Activity Restarted");
        if (player != null) {
            player.resume();
        }
    }

    /**
     * Listen to all notifications from the OoyalaPlayer
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        if (arg1 == OoyalaPlayer.TIME_CHANGED_NOTIFICATION) {
            return;
        }
        Log.d(TAG, "Notification Received: " + arg1 + " - state: " + player.getState());
    }

}

