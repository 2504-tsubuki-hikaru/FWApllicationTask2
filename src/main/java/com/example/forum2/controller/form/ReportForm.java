package com.example.forum2.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportForm {

    private int id;
    @NotBlank(message="投稿内容を入力してください")
    private String content;
}
