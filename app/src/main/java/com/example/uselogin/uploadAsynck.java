package com.example.uselogin;

import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class uploadAsynck extends AsyncTaskLoader<Integer> {
    String  Title,Abstract, Protype;
    byte[] Content;
    public uploadAsynck(@NonNull Context context,String  title,String abstractf, String protype, byte[] content) {
        super(context);
        this.Title =title;
        this.Abstract = abstractf;
        this.Protype = protype;
        this.Content = content;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        Connection connection;
        Dbconnection mConnection = new Dbconnection();
        connection = mConnection.data_connection();
        int result = 0;
        if (connection == null) {
            return null;
        } else {
            try {
                CallableStatement preparedStatement = connection.prepareCall("EXEC StorePDF(?,?,?,?)");
                preparedStatement.setString(1, Title);
                preparedStatement.setString(2, Abstract);
                preparedStatement.setString(3,Protype);
                preparedStatement.setBytes(4, Content);
                result = preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
