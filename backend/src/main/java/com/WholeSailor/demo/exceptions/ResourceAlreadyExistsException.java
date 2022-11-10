package com.WholeSailor.demo.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException{
    String resourceName;

    public ResourceAlreadyExistsException(String resourceName){
        super(String.format("Resource %s with given parameters already exists.", resourceName));

        this.resourceName = resourceName;
    }

}
