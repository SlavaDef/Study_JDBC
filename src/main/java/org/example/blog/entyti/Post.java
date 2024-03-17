package org.example.blog.entyti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Long post_id;
    private String text;
    private User author;
    private LocalDateTime createdIn;

    public Post(String text, User author, LocalDateTime createdIn) {
        this.text = text;
        this.author = author;
        this.createdIn = createdIn;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", createdIn=" + createdIn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return Objects.equals(getPost_id(), post.getPost_id()) && Objects.equals(getText(), post.getText()) && Objects.equals(getAuthor(), post.getAuthor()) && Objects.equals(getCreatedIn(), post.getCreatedIn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPost_id(), getText(), getAuthor(), getCreatedIn());
    }
}
