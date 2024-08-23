package com.example.uselogin;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//ASYNCK TASKLOADER THAT FUNCTION AT BACKGROUND
public class LoginTask extends AsyncTaskLoader<Integer> {
    String Username, Password;
    public LoginTask(@NonNull Context context, String username, String password) {
        super(context);
        this.Username = username;
        this.Password = password;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        int response =0;
        Connection connection;
        Dbconnection mConnection = new Dbconnection();
        connection = mConnection.data_connection();
          if (connection == null) {
               response =2;
            } else {
                try {
                    CallableStatement preparedStatement = connection.prepareCall("EXEC Usernamepara(?,?)");
                    preparedStatement.setString(1, Username);
                    preparedStatement.setString(2, Password);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                    response  = 1;
                    }
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    return 3;
                }
            }
          return response;
        }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }
}
