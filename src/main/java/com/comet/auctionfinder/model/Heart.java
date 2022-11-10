package com.comet.auctionfinder.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String court;

    private String auctionValue; //2020경~

    @OneToMany(mappedBy = "heart")
    private List<MemberHeart> heartList = new ArrayList<>();

    public void appendHeart(MemberHeart heart) {
        if (heart.getHeart() != null)
            heart.getHeart().removeHeart(heart);
        if (!heartList.contains(heart))
            heartList.add(heart);
        heart.setHeart(this);

    }

    public void removeHeart(MemberHeart heart) {
        heartList.remove(heart);
        heart.setHeart(null);
    }
}
