package com.cloud.member.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : xiemogaminari
 * create at:  2020-10-25  01:04
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberVo {
    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String phoneNum;

    private String code;
}