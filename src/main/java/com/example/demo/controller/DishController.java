package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DishController {

	//一覧画面表示
	@GetMapping("/dishes/result")
	public String index() {
		return "dishes-result";
	}

	//新規登録画面表示
	@GetMapping("/dishes/add")
	public String create() {
		return "dishes-add";
	}

	//メモ登録画面表示
	@GetMapping("/dishes/note")
	public String note() {
		return "dishes-note";
	}
}