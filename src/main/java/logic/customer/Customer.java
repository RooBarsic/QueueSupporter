package logic.customer;

import org.jetbrains.annotations.NotNull;

/**
 * Класс для хранения данных о клиентах.
 * Храним имя клиента и номер его телефона
 * Author: Farrukh Karimov
 * Modification Date: 09.02.2020
 */
public class Customer implements PhoneAvailable {
    private String name;
    @NotNull
    private final String phoneNumber;

    public Customer(final  String customerName, @NotNull final  String phoneNumber){
        this.name = customerName;
        this.phoneNumber = phoneNumber;
    }

    public Customer(@NotNull final String phoneNumber){
        this.name = "-";
        this.phoneNumber = phoneNumber;
    }

    public String getName(){
        return name;
    }

    public void setName(final String customerName){
        name = customerName;
    }

    @Override
    public @NotNull String getPhoneNumber() {
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
        return getPhoneNumber().hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}