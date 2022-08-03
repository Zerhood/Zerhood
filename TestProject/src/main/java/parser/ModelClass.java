package parser;

import model.Address;

import java.util.List;

public interface ModelClass {
    List<Address> getAddresses();

    boolean isCorrect(String[] array);
}