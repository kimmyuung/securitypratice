package oauth2web.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString
@Builder
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    private String mid;
    private String mpassword;
    private Role role;
}
