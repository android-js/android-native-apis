package com.android.js.react_native;

import android.app.Activity;
import android.os.Bundle;

import com.android.js.other.Utils;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactPackage;

import java.io.File;
import java.util.List;

public class AndroidJSActivity extends ReactActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("node");
    }

    //We just want one instance of node running in the background.
    public static boolean _startedNodeAlready=false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void start_node(final Activity activity){
        if( !_startedNodeAlready ) {
            _startedNodeAlready=true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //The path where we expect the node project to be at runtime.
                    String nodeDir = activity.getApplicationContext().getFilesDir().getAbsolutePath()+"/scripts";
                    if (Utils.wasAPKUpdated(activity.getApplicationContext())) {
                        //Recursively delete any existing nodejs-project.
                        File nodeDirReference=new File(nodeDir);
                        if (nodeDirReference.exists()) {
                            Utils.deleteFolderRecursively(new File(nodeDir));
                        }
                        //Copy the node project from assets into the application's data path.
                        Utils.copyAssetFolder(activity.getApplicationContext().getAssets(), "scripts", nodeDir);

                        Utils.saveLastUpdateTime(activity.getApplicationContext());
                    }
                    startNodeWithArguments(new String[]{"node",
                            nodeDir+"/main.js"
                    });
                }
            }).start();
        }
    }

    @Override
    protected String getMainComponentName() {
        return "androidjs_core_react_native";
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return false;
    }

    @Override
    protected List<ReactPackage> getPackages() {
        return null;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native Integer startNodeWithArguments(String[] arguments);
}
