package ORM_EXE;

import java.time.LocalDate;

public class User {

    @Primary_Key
    @Auto_Increment
    private int id;

    @Primary_Key
    @Not_Null
    @Unique
    private String email;

    @Not_Null
    private String name;
    private double weight;
    private int age;
    private char grade;
    private LocalDate birthDate;
    private boolean employed;

    public User(){

    }

}
