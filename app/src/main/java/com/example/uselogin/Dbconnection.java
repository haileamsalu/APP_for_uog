package com.example.uselogin;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnection {
    @SuppressLint("Uselogin")
    public Connection data_connection() {
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        Connection connection = null;
        String ConnectionUrl;
        try {
            String Drivers = "net.sourceforge.jtds.jdbc.Driver";
            Class.forName(Drivers);
            ConnectionUrl = "jdbc:jtds:sqlserver://192.168.137.1:4444/LoginDB;instance=MSSQLSERVER;user=sa;password=33560308;";
            connection = DriverManager.getConnection(ConnectionUrl);
        } catch (SQLException sqlexception) {
            Log.e("Error from SQL", sqlexception.getMessage());
        } catch (ClassNotFoundException except) {
            Log.e("Error No Class", except.getMessage());
        } catch (Exception ex) {
            Log.e("Error from Class", ex.getMessage());
        }
        return connection;
    }
}
