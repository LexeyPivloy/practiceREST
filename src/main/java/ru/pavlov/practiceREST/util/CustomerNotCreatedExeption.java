package ru.pavlov.practiceREST.util;


public class CustomerNotCreatedExeption extends RuntimeException{
    public CustomerNotCreatedExeption(String msg){
        super(msg);
    }
}