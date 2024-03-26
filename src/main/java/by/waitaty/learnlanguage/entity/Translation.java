package by.waitaty.learnlanguage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long idWord;
    @Column(name = "translation")
    private Long idTranslationWord;
    private Long numberOfUses;
    @Column(name = "is_approved")
    private boolean isApproved;
    @Column(name = "is_private")
    private boolean isPrivate;
}
