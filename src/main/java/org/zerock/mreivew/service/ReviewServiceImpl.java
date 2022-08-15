package org.zerock.mreivew.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mreivew.dto.ReviewDto;
import org.zerock.mreivew.entity.Movie;
import org.zerock.mreivew.entity.Review;
import org.zerock.mreivew.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor

public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    // 목록 조회를 왜 스트림이랑 맵을 써서하는지 이해가 안가나ㅔ.....................................
    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List< Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());
    }

    // 등록
    @Override
    public Long register(ReviewDto movieReviewDto) {

        Review movieReview = dtoToEntity(movieReviewDto);
        reviewRepository.save(movieReview);

        return movieReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDto movieReviewDto) {

        Optional<Review> result = reviewRepository.findById(movieReviewDto.getReviewnum());
        if(result.isPresent()) {

            Review movieReview = result.get();
            movieReview.changeGrade(movieReviewDto.getGrade());
            movieReview.changeText(movieReviewDto.getText());

            reviewRepository.save(movieReview);
        }

    }

    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }
}
