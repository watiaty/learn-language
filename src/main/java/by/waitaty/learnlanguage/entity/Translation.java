package by.waitaty.learnlanguage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "translation")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_word", nullable = false)
    @JsonIgnore
    private Word word;

    @ManyToOne
    @JoinColumn(name = "translation", nullable = false)
    private Word translation;
    private Long numberOfUses;
    @Column(name = "is_approved")
    private boolean isApproved;
    @Column(name = "is_private")
    private boolean isPrivate;

    @Override
    public String toString() {
        return "Translation{" +
                "id=" + id +
                ", translation='" + translation.getWord() + '\'' +
                ", numberOfUses=" + numberOfUses +
                ", isApproved=" + isApproved +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
