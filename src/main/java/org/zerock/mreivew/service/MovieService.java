package org.zerock.mreivew.service;

import org.springframework.data.domain.PageRequest;
import org.zerock.mreivew.dto.MovieDto;
import org.zerock.mreivew.dto.MovieImageDto;
import org.zerock.mreivew.dto.PageRequestDto;
import org.zerock.mreivew.dto.PageResultDto;
import org.zerock.mreivew.entity.Movie;
import org.zerock.mreivew.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    MovieDto getMovie(Long mno);

    Long register(MovieDto movieDto);

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto requestDto); //목록 처리

    default MovieDto entitiesToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reivewCnt) {
        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDto> movieImageDtoList = movieImages.stream().map(movieImage -> {
            return MovieImageDto.builder().imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDto.setImageDtoList(movieImageDtoList);
        movieDto.setAvg(avg);
        movieDto.setReviewCnt(reivewCnt.intValue());

        return movieDto;
    }


    default Map<String, Object> dtoToEntity(MovieDto movieDto) {
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDto.getMno())
                .title(movieDto.getTitle())
                .build();

        entityMap.put("movie", movie);

        List<MovieImageDto> imageDtoList = movieDto.getImageDtoList();

        //Movie ImageDto처리

        if(imageDtoList != null && imageDtoList.size() > 0 ) {
            List< MovieImage> movieImageList = imageDtoList.stream().map(movieImageDto -> {

                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDto.getPath())
                        .imgName(movieImageDto.getImgName())
                        .uuid(movieImageDto.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());
        }
        return entityMap;
    }
}
