package com.comet.auctionfinder.controller.api;

import com.comet.auctionfinder.component.AuctionParser;
import com.comet.auctionfinder.dto.HeartRequestDto;
import com.comet.auctionfinder.dto.HeartResponseDto;
import com.comet.auctionfinder.exception.HeartNotFoundException;
import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.service.HeartService;
import com.comet.auctionfinder.util.AuctionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RestController()
@RequestMapping("/api")
@AllArgsConstructor
public class AuctionApiController {

    private AuctionParser parser;
    private HeartService service;

    @GetMapping("/auction")
    public CompletableFuture<ResponseEntity<List<AuctionSimple>>> auctions(@RequestParam String pro, @RequestParam String city) {
        String province = parser.matchProvince(pro);
        return parser.parseData(province, city).thenApplyAsync((result) -> {
            if (result.getFirst() == AuctionResponse.FOUND) {
                return new ResponseEntity<>(result.getSecond(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(result.getSecond(), HttpStatus.BAD_REQUEST); //임시로 처리
            }
        });
    }

    @GetMapping("/city")
    public CompletableFuture<ResponseEntity<List<String>>> cities(@RequestParam String pro) {
        return parser.getCities(pro).thenApplyAsync((result) -> new ResponseEntity<>(result, HttpStatus.OK));
    }

    @GetMapping("/heart")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HeartResponseDto>> getHearts(@AuthenticationPrincipal UserDetails principal) {
        if (checkAjax(principal))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        else {
            String username = principal.getUsername();
            return new ResponseEntity<>(service.getMemberHearts(username), HttpStatus.OK);
        }
    }

    //isAuthorized 로 합쳐도 될듯
    //custom userdetail service이므로 userdetail로 받아야됨
    @GetMapping("/heart/{auctionValue}")
    @PreAuthorize("isAuthenticated()")
    public @Nullable ResponseEntity<HeartResponseDto> getHeart(@PathVariable String auctionValue, @AuthenticationPrincipal UserDetails principal) throws Exception {
        if (checkAjax(principal))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = principal.getUsername();
        Optional<HeartResponseDto> dto = service.getMemberHeart(username, auctionValue);
        if (dto.isEmpty())
            throw new HeartNotFoundException("Heart Not Found");
        return new ResponseEntity<>(dto.get(), HttpStatus.OK);
    }

    @PutMapping("/heart") //modelattribute == bindException
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> addHeart(@Valid HeartRequestDto dto, @AuthenticationPrincipal UserDetails principal) {
        if (checkAjax(principal))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = principal.getUsername();
        service.addHeart(dto, username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/heart") //modelattribute == bindException
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> removeHeart(@Valid HeartRequestDto dto, @AuthenticationPrincipal UserDetails principal) {
        if (checkAjax(principal))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = principal.getUsername();
        return new ResponseEntity<>(service.removeHeart(username, dto), HttpStatus.OK);
    }

    private boolean checkAjax(UserDetails principal) {
        return principal == null;
    }


}
