package org.example.blog.entyti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long user_id;
    private String fullName;
    private String pseudonym;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUser_id(), user.getUser_id()) && Objects.equals(getFullName(), user.getFullName()) && Objects.equals(getPseudonym(), user.getPseudonym()) && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser_id(), getFullName(), getPseudonym(), getEmail());
    }
}
