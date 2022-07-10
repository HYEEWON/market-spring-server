package com.market.baechoo.controller;

import com.market.baechoo.common.ResponseCreator;
import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.dto.MemberLoginDto;
import com.market.baechoo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private ResponseCreator responseCreator;

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
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 입력입니다.'); </script>");
            out.flush();
            return "forward:/join";
            //return "<script>alert('아이디는 5~20자의 영문 소문자와 숫자만 사용 가능합니다.'); </script>";
        }
        System.out.println("join controller ############################");
        memberService.join(memberDTO);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginDto memberLoginDto, BindingResult bindingResult
    , Model model, HttpServletResponse response) throws IOException {
        if (bindingResult.hasErrors()) {
            response.setContentType("text/html; charset=euc-kr");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 입력입니다.'); </script>");
            out.flush();
            return "forward:/login";
        }
        String result = memberService.login(memberLoginDto);
        if (result.equals("1") || result.equals("2")) {
            responseCreator.createAlert(response, "존재하지 않는 아이디 또는 잘못된 비밀번호입니다.");
            return "forward:/login";
        }
        model.addAttribute("jwt", result);
        System.out.println(result);
        return "redirect:/";
    }
}
