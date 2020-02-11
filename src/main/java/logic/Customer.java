package logic;

import java.util.Objects;

/**
 * Date: 09.02.2020
 * Author: Farrukh Karimov
 */
public class Customer {
    private String name;
    private String phoneNumber;

    public Customer(final String customerName, final String phoneNumber){
        this.name = customerName;
        this.phoneNumber = phoneNumber;
    }

    public Customer(final String phoneNumber){
        this.name = "";
        this.phoneNumber = phoneNumber;
    }

    public String getName(){
        return name;
    }

    public void setName(final String customerName){
        name = customerName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getPhoneNumber().equals(customer.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumber());
    }
}
