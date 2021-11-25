package ru.mirea.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "log")
    private String username;

    @Column(name = "par")
    private String password;

    @Column(name = "ico")
    private int icons;

    @Column(name = "shr")
    private int sohr;

    public boolean conf_auth(String pass)
    {
        return password.equals(pass);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", icons=" + icons +
                ", sohr=" + sohr +
                '}';
    }
}
