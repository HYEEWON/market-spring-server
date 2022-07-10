package com.market.baechoo.controller;

import com.market.baechoo.common.ResponseCreator;
import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.dto.MemberLoginDto;
import com.market.baechoo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private ResponseCreator responseCreator;

    @Value("${jwt.header}")
    private String AUTHORIZATION_HEADER;

    @GetMapping("/join")
    public String join() {
        return "account/join";
    }

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberJoinDto memberDTO, BindingResult bindingResult,
                       HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            responseCreator = new ResponseCreator();
            responseCreator.createAlert(response, "잘못된 입력입니다.");
            return "forward:/join";
            //return "<script>alert('아이디는 5~20자의 영문 소문자와 숫자만 사용 가능합니다.'); </script>";
        }
        memberService.join(memberDTO);
        return "redirect:/";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@ModelAttribute MemberLoginDto memberLoginDto, BindingResult bindingResult
    , Model model, HttpServletResponse response) throws IOException, URISyntaxException {
        responseCreator = new ResponseCreator();

        String result = memberService.login(memberLoginDto);
        if (bindingResult.hasErrors() || result.equals("1") || result.equals("2")) {
            return new ResponseEntity<>("<script>alert('" + "존재하지 않는 아이디 또는 잘못된 비밀번호입니다." + "'); location.href='" +
                    "http://localhost:8080/account/login" + "';</script>", HttpStatus.FOUND);
        }

        System.out.println("result:" + result);

        URI redirectUri = new URI("http://localhost:8080");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + result);

        return new ResponseEntity<>(result, httpHeaders, HttpStatus.FOUND);
    }
}
