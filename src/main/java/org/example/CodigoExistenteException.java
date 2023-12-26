package org.example;

public class CodigoExistenteException extends Exception{
    public CodigoExistenteException(){
        super("Id já existente");
    }
}
