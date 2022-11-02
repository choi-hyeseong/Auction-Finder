package com.comet.auctionfinder.controller;

import com.comet.auctionfinder.component.AuctionParser;
import com.comet.auctionfinder.model.AuctionSimple;
import com.comet.auctionfinder.util.AuctionResponse;
import com.comet.auctionfinder.util.Twin;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

    private AuctionParser parser;

    @GetMapping("/auction")
    public CompletableFuture<ResponseEntity<List<AuctionSimple>>> auctions(@RequestParam String pro, @RequestParam String city) throws Exception {
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
}
