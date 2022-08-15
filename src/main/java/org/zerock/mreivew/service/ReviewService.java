package org.zerock.mreivew.service;

import org.zerock.mreivew.dto.ReviewDto;
import org.zerock.mreivew.entity.Member;
import org.zerock.mreivew.entity.Movie;
import org.zerock.mreivew.entity.Review;

import java.util.List;

public interface ReviewService {


    // 영화의 모든 리뷰
    List<ReviewDto> getListOfMovie(Long mno);

    Long register(ReviewDto movieReviewDto);

    void modify(ReviewDto movieReviewDto);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDto movieReviewDto) {

        Review movieReview = Review.builder()
                .reviewnum(movieReviewDto.getReviewnum())
                .movie(Movie.builder().mno(movieReviewDto.getMno()).build())
                .member(Member.builder().mid(movieReviewDto.getMid()).build())
                .grade(movieReviewDto.getGrade())
                .text(movieReviewDto.getText())
                .build();
        return movieReview;

    }

    default ReviewDto entityToDto(Review movieReview) {

        ReviewDto movieReviewDto = ReviewDto.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDto;
    }
}
