package com.baechoo.member.controller;

import com.baechoo.member.dto.MemberDTO;
import com.baechoo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class JoinController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/join")
    public String join() {
        return "/join";
    }

    //@PostMapping("/join")
    /*public String join(@Valid MemberDTO memberDTO) {
        memberService.save(memberDTO);
    }*/
}
