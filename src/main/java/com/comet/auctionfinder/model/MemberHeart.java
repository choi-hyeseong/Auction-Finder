package com.comet.auctionfinder.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.CascadeType;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberHeart extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //어처피 delete는 안먹힘 그래서 걍 all 써도 되는듯..
    //근데 remove는 좀 위험해서 일단 그거 빼고 적용

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REFRESH})
    @JoinColumn(name = "heart_id")
    private Heart heart;
}
