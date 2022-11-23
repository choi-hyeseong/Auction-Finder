package com.comet.auctionfinder.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    private String title;

    private String content;

    private int viewCount;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
