package com.learning.electronic.bean.user;

import lombok.Data;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/5/7 - 10:53
 * @history 2020/5/7 - 10:53 chenglonghy  create.
 */
@Data
public class Role {
    private int id;
    private int userId;
    private String role;
}
