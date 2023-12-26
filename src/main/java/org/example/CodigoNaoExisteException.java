package org.example;

public class CodigoNaoExisteException extends Exception{
    public CodigoNaoExisteException(){
        super  ("Id não existe");
    }
}
