package model.exceptions;

import model.dataBase.DBManager;

public class DBManagerException extends Exception {

    public DBManagerException(){
        super();
    }

    public DBManagerException(String message){
        super(message);
    }

    public DBManagerException(String message, Throwable cause){
        super(message, cause);
    }

    public DBManagerException(Throwable cause){
        super(cause);
    }
}
