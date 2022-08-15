package org.zerock.mreivew.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreivew.dto.ReviewDto;
import org.zerock.mreivew.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor

public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getList(@PathVariable("mno") Long mno) {
        List<ReviewDto> reviewDtoList = reviewService.getListOfMovie(mno);
        return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto movieReviewDto) {
        Long reviewnum = reviewService.register(movieReviewDto);
        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum, @RequestBody ReviewDto moviewReviewDto) {
        reviewService.modify(moviewReviewDto);
        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum) {
        reviewService.remove(reviewnum);
        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }
}
