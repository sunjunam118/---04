package org.zerock.mreivew.service;

import jdk.jfr.Frequency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mreivew.dto.MovieDto;
import org.zerock.mreivew.dto.MovieImageDto;
import org.zerock.mreivew.dto.PageRequestDto;
import org.zerock.mreivew.dto.PageResultDto;
import org.zerock.mreivew.entity.Movie;
import org.zerock.mreivew.entity.MovieImage;
import org.zerock.mreivew.repository.MovieImageRepository;
import org.zerock.mreivew.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor

public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    // 조회 페이지와 영화 리뷰

    @Override
    public MovieDto getMovie(Long mno) {

        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>(); // 영화의 이미지 개수만큼 무비 이미지 객체 필요..?

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);

        });

        Double avg = (Double) result.get(0)[2];  // 평균평점
        Long reviewCnt = (Long) result.get(0)[3]; //리뷰 개수
        return entitiesToDto(movie, movieImageList, avg, reviewCnt);
    }

    // 등록 처리 리포와 이미지리포를 주입받도록 구성, DtoEntity에서 반환한 객체를 이용해서 save처리
    @Override
    public Long register(MovieDto movieDto) {
        Map<String, Object> entityMap = dtoToEntity(movieDto);
        Movie movie = (Movie) entityMap.get("movie");
        List< MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);
        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });
        return movie.getMno();
    }



    // 목록처리와 평균평점
    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDto> fn = ( arr -> entitiesToDto(
                (Movie) arr[0],
                (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long) arr[3])
        );
        return new PageResultDto<>(result, fn);
    }
}
