package com.gbase.admin.common.exception.ops;

public class UserAlreadyExistsException extends OpsException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
