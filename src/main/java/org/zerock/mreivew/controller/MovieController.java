package org.zerock.mreivew.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreivew.dto.MovieDto;
import org.zerock.mreivew.dto.PageRequestDto;
import org.zerock.mreivew.service.MovieService;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/register")
    public void register() {

    }
    // post방식으로 전달된 피라미터들을 dto로 수집해서 service 타입 객체의 register를 호출하도록 작성
    @PostMapping("/register")
    public String register(MovieDto movieDto, RedirectAttributes redirectAttributes) {

        Long mno = movieService.register(movieDto);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        model.addAttribute("result", movieService.getList(pageRequestDto));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long mno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        MovieDto movieDto = movieService.getMovie(mno);
        model.addAttribute("dto", movieDto);
    }

}
