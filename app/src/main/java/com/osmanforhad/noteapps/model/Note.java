package com.osmanforhad.noteapps.model;

public class Note {

    /* variable declaration **/
    private String title;
    private String content;

    /* constructor method with empty**/
    public Note(){

    }//end of the empty constructor

    /* constructor method with parameter **/
    public Note(String title, String content){
        this.title = title;
        this.content = content;
    }//end of the constructor method

    /* start setter and getter method **/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    /* end of the setter and getter method **/

}//end of the class
