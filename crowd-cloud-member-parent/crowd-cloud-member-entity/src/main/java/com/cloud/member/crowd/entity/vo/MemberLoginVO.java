package com.cloud.member.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : xiemogaminari
 * create at:  2020-10-24  23:56
 * @description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String email;
}