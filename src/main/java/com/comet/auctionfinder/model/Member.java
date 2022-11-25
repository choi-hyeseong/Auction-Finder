package com.comet.auctionfinder.model;

import com.comet.auctionfinder.dto.MemberUpdateDto;
import com.comet.auctionfinder.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 30)
    private String userId; //id

    @Column(nullable = false, unique = true, length = 12)
    private String nickName;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false)
    private String password; //password

    @Enumerated
    private UserRole role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<MemberHeart> heartList = new ArrayList<>();

    public void addHeart(MemberHeart heart) {
        //하트는 정적/불변, 해당 메소드 추가전 Heart에서도 동일하게 처리
        if (heart.getMember() != null)
            heart.getMember().removeHeart(heart);
        if (!heartList.contains(heart))
            heartList.add(heart);
        heart.setMember(this);

    }

    public void removeHeart(MemberHeart heart) {
        heartList.remove(heart);
        heart.setMember(null);
    }

    public void update(MemberUpdateDto dto) {
        this.email = dto.getEmail();
        this.nickName = dto.getNickName();
    }



}
