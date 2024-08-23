package com.example.uselogin;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class fetchtableasyncktask extends AsyncTaskLoader<ArrayList<uploadfilemodel>> {

   public fetchtableasyncktask(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public ArrayList<uploadfilemodel> loadInBackground() {
        ArrayList<uploadfilemodel> mymodels = new ArrayList<>();
        Connection connection;
        Dbconnection mConnection = new Dbconnection();
        connection = mConnection.data_connection();
        if (connection == null) {
            return null;
        } else {
            try {
                CallableStatement preparedStatement = connection.prepareCall("EXEC Documents");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    uploadfilemodel model = new uploadfilemodel();
                    model.setTitle(resultSet.getString("Title"));
                    model.setAbstract(resultSet.getString("Absract"));
                    model.setProjectType(resultSet.getString("Protype"));
                    mymodels.add(model);
                }
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mymodels;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}