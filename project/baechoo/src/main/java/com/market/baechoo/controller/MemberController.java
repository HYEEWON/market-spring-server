package com.market.baechoo.controller;

import com.market.baechoo.dto.MemberJoinDto;
import com.market.baechoo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
            out.println("<script>alert('아이디는 5~20자의 영문 소문자와 숫자만 사용 가능합니다.'); </script>");
            out.flush();
            return "forward:/join";
            //return "<script>alert('아이디는 5~20자의 영문 소문자와 숫자만 사용 가능합니다.'); </script>";
        }
        memberService.join(memberDTO);
        return "redirect:/";
    }
}
