package com.kborid.smart.test;

import com.kborid.library.util.LogUtils;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public abstract class AExecuteAsRoot {

    private static final String TAG = "ROOT";

    public static boolean canRunRootCommands() {
        boolean retval = false;
        Process suProcess;

        try {
            suProcess = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream osRes = new DataInputStream(suProcess.getInputStream());

            // Getting the id of the current user to check if this is root
            os.writeBytes("id\n");
            os.flush();

            String currUid = osRes.readLine();
            boolean exitSu = false;
            if (null == currUid) {
                retval = false;
                exitSu = false;
                Logger.t(TAG).d("Can't get root access or denied by user");
            } else if (currUid.contains("uid=0")) {
                retval = true;
                exitSu = true;
                Logger.t(TAG).d("Root access granted");
            } else {
                retval = false;
                exitSu = true;
                Logger.t(TAG).d("Root access rejected: " + currUid);
            }

            if (exitSu) {
                os.writeBytes("exit\n");
                os.flush();
            }
        } catch (Exception e) {
            // Can't get root !
            // Probably broken pipe exception on trying to write to output
            // stream after su failed, meaning that the device is not rooted
            retval = false;
            Logger.t(TAG).d("Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
        }

        return retval;
    }

    public final boolean execute() {
        boolean retval = false;

        try {
            ArrayList<String> commands = getCommandsToExecute();
            if (null != commands && commands.size() > 0) {
                Process process = Runtime.getRuntime().exec("su");

                DataOutputStream os = new DataOutputStream(process.getOutputStream());

                for (String currCommand : commands) {
                    os.writeBytes(currCommand + "\n");
                    os.flush();
                }

                os.writeBytes("exit\n");
                os.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                int read;
                char[] buffer = new char[4096];
                StringBuffer output = new StringBuffer();
                while ((read = reader.read(buffer)) > 0) {
                    output.append(buffer, 0, read);
                }
                reader.close();

                try {
                    int suProcessRetval = process.waitFor();
                    retval = 255 != suProcessRetval;
                    LogUtils.d(TAG, output.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            Logger.t(TAG).d("Can't get root access", ex);
        } catch (SecurityException ex) {
            Logger.t(TAG).d("Can't get root access", ex);
        } catch (Exception ex) {
            Logger.t(TAG).d("Error executing internal operation", ex);
        }

        return retval;
    }

    protected abstract ArrayList<String> getCommandsToExecute();
}
