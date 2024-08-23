package com.example.uselogin;

public class uploadfilemodel {
    String  Title,Abstract,ProjectType;


    public uploadfilemodel() {
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setAbstract(String anAbstract) {
        this.Abstract = anAbstract;
    }
    public String getAbstract() {
        return Abstract;
    }
    public String getProjectType(){ return  ProjectType;}

    public void setProjectType(String projectType) {
        this.ProjectType = projectType;
    }
}
