package com.comet.auctionfinder.dto;

import com.comet.auctionfinder.model.Heart;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeartRequestDto {

    @NotBlank(message = "법원값은 반드시 작성되어야 합니다.")
    private String court;
    @NotBlank(message = "사건번호는 반드시 작성되어야 합니다.")
    @Pattern(regexp = "[0-9]{4}타경[0-9]{3,}", message = "법원 번호 양식에 맞지 않습니다.")
    private String auctionValue;

    public Heart toEntity() {
        return Heart.builder().heartList(new ArrayList<>()).auctionValue(auctionValue).court(court).build();
    }
}
