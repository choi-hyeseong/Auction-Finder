package com.comet.auctionfinder.service;

import com.comet.auctionfinder.dto.HeartRequestDto;
import com.comet.auctionfinder.dto.HeartResponseDto;
import com.comet.auctionfinder.model.Heart;
import com.comet.auctionfinder.model.Member;
import com.comet.auctionfinder.model.MemberHeart;
import com.comet.auctionfinder.repository.HeartRepository;
import com.comet.auctionfinder.repository.MemberHeartRepository;
import com.comet.auctionfinder.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HeartService {

    private MemberRepository repository;
    private HeartRepository heartRepository;
    private MemberHeartRepository memberHeartRepository;

    @Transactional
    public void addHeart(HeartRequestDto dto, String username) {
        Heart heart;
        Member member;
        MemberHeart memberHeart;
        String court = dto.getCourt();
        String auctionValue = dto.getAuctionValue();
        Optional<Heart> result = heartRepository.findByCourtAndAuctionValue(court, auctionValue);
        Optional<Member> memberResult = repository.findByUserId(username);
        member = memberResult.orElseThrow();
        if (result.isEmpty()) {
            //하트가 없는경우
            heart = dto.toEntity();
            memberHeart = new MemberHeart();
        }
        else {
            //하트가 있는경우
            heart = result.get();
            Optional<MemberHeart> heartResult = memberHeartRepository.findByHeartAndMember(heart, member); //이거 메소드 선언한 순서랑 파라미터 안맞으면 api exception 띄우넹..
            memberHeart = heartResult.orElseGet(MemberHeart::new);
        }
        //기존코드
            /*heartRepository.save(heart); //오류 방지 = 하트가 생성되어 있지 않으면 memberheart를 가져올 수 없음.
        }
        else
            heart = result.get();*/
        heart.appendHeart(memberHeart);
        member.addHeart(memberHeart);
        memberHeartRepository.save(memberHeart); //manytoone => 저장상태 전이됨. (cascade선언으로 필드 set이랑 list 추가만 잘하면 저장됨)
    }

    @Transactional(readOnly = true)
    public List<HeartResponseDto> getMemberHearts(String username) {
        Member member = repository.findByUserId(username).orElseThrow();
        if (member.getHeartList().isEmpty())
            return new ArrayList<>();
        return member.getHeartList().stream().map(HeartResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public Optional<HeartResponseDto> getMemberHeart(String username, String auctionValue) {
        Member member = repository.findByUserId(username).orElseThrow();
        return member.getHeartList().isEmpty() ? Optional.empty() : member.getHeartList().stream()
                .filter(memberHeart -> memberHeart.getHeart().getAuctionValue().equals(auctionValue))
                .map(HeartResponseDto::new).findFirst();
    }

    @Transactional
    public boolean removeHeart(String username, HeartRequestDto dto) {
        Member member = repository.findByUserId(username).orElseThrow();
        String court = dto.getCourt();
        String auctionValue = dto.getAuctionValue();
        Optional<Heart> result = heartRepository.findByCourtAndAuctionValue(court, auctionValue);
        if (result.isEmpty())
            return false;
        else {
            Heart heart = result.get();
            Optional<MemberHeart> heartResult = memberHeartRepository.findByHeartAndMember(heart, member);
            if (heartResult.isEmpty())
                return false;
            else {
                MemberHeart memberHeart = heartResult.get();
                member.removeHeart(memberHeart);
                heart.removeHeart(memberHeart);
                memberHeartRepository.delete(memberHeart); //하트를 냅두는 이유 => 재활용 가능
                return true;
            }
        }
    }
}
