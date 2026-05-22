package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Result;
import com.example.demo.repository.ResultRepository;

@Controller
public class DishController {

	private final HttpSession session;
	private final ResultRepository resultRepository;

	public DishController(HttpSession session, ResultRepository resultRepository) {
		this.session = session;
		this.resultRepository = resultRepository;
	}

	//一覧画面表示
	@GetMapping("/dishes/result")
	public String index(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		List<Result> resultList = resultRepository.findByUserId(userId);
		model.addAttribute("results", resultList);
		return "dishes-result";
	}

	//新規登録画面表示
	@GetMapping("/dishes/add")
	public String create() {
		return "dishes-add";
	}

	//新規登録
	@PostMapping("/dishes/add")
	public String store(
			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo,
			Model model) {

		List<String> errorList = new ArrayList<>();
		if (stapleFood == null) {
			errorList.add("主食を選択してください");
		}
		if (sideDish == null) {
			errorList.add("副菜を選択してください");
		}
		if (mainDish == null) {
			errorList.add("主菜を選択してください");
		}
		if (milkDish == null) {
			errorList.add("乳製品を選択してください");
		}
		if (fruitCount == null) {
			errorList.add("果物を選択してください");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("stapleFood", stapleFood);
			model.addAttribute("sideDish", sideDish);
			model.addAttribute("mainDish", mainDish);
			model.addAttribute("milkDish", milkDish);
			model.addAttribute("fruitCount", fruitCount);
			model.addAttribute("detailMemo", detailMemo);
			return "dishes-add";
		}

		Integer userId = (Integer) session.getAttribute("userId");
		Result result = new Result();

		result.setUserId(userId);
		result.setRecordDate(LocalDate.now());
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detailMemo);
		int achievement = sumAchievement(stapleFood, sideDish, mainDish,
				milkDish, fruitCount);
		result.setAchievement(achievement);
		resultRepository.save(result);

		return "redirect:/dishes/result";
	}

	//メモ登録画面表示
	@GetMapping("/dishes/note")
	public String note(
			@RequestParam(defaultValue = "") Integer stapleFood,
			@RequestParam(defaultValue = "") Integer sideDish,
			@RequestParam(defaultValue = "") Integer mainDish,
			@RequestParam(defaultValue = "") Integer milkDish,
			@RequestParam(defaultValue = "") Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo,
			Model model) {
		model.addAttribute("stapleFood", stapleFood);
		model.addAttribute("sideDish", sideDish);
		model.addAttribute("mainDish", mainDish);
		model.addAttribute("milkDish", milkDish);
		model.addAttribute("fruitCount", fruitCount);
		model.addAttribute("detailMemo", detailMemo);
		return "dishes-note";

	}

	//更新画面表示
	@GetMapping("/dishes/{id}/edit")
	public String edit(
			@PathVariable Integer id,
			Model model) {
		Result result = resultRepository.findById(id).get();
		model.addAttribute("result", result);
		return "dishes-edit";
	}

	//更新処理
	@PostMapping("/dishes/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam(defaultValue = "") LocalDate recordDate,
			@RequestParam(required = false) Integer stapleFood,
			@RequestParam(required = false) Integer sideDish,
			@RequestParam(required = false) Integer mainDish,
			@RequestParam(required = false) Integer milkDish,
			@RequestParam(required = false) Integer fruitCount,
			@RequestParam(defaultValue = "") String detailMemo,
			Model model) {

		Result result = resultRepository.findById(id).get();

		List<String> errorList = new ArrayList<>();

		if (stapleFood == null) {
			errorList.add("主食を選択してください");
		}
		if (sideDish == null) {
			errorList.add("副菜を選択してください");
		}
		if (mainDish == null) {
			errorList.add("主菜を選択してください");
		}
		if (milkDish == null) {
			errorList.add("乳製品を選択してください");
		}
		if (fruitCount == null) {
			errorList.add("果物を選択してください");
		}

		if (errorList.size() > 0) {
			result.setStapleFood(stapleFood);
			result.setSideDish(sideDish);
			result.setMainDish(mainDish);
			result.setMilkDish(milkDish);
			result.setFruitCount(fruitCount);
			result.setDetailMemo(detailMemo);

			model.addAttribute("errorList", errorList);
			model.addAttribute("result", result);

			return "dishes-edit";
		}

		result.setRecordDate(recordDate);
		result.setStapleFood(stapleFood);
		result.setSideDish(sideDish);
		result.setMainDish(mainDish);
		result.setMilkDish(milkDish);
		result.setFruitCount(fruitCount);
		result.setDetailMemo(detailMemo);
		int achievement = sumAchievement(stapleFood, sideDish, mainDish,
				milkDish, fruitCount);
		result.setAchievement(achievement);
		resultRepository.save(result);

		return "redirect:/dishes/result";
	}

	//食事バランス計算
	private int sumAchievement(Integer stapleFood, Integer sideDish, Integer mainDish,
			Integer milkDish, Integer fruitCount) {
		int achievement = 88;

		//主食計算
		if (stapleFood >= 5 && stapleFood <= 7) {
			achievement -= 0;
		} else if (stapleFood < 5) {
			achievement -= (5 - stapleFood) * 4;
		} else {
			achievement -= (stapleFood - 7) * 4;
		}

		//		副菜計算
		if (sideDish >= 5 && sideDish <= 6) {
			achievement -= 0;
		} else if (sideDish < 5) {
			achievement -= (5 - sideDish) * 4;
		} else {
			achievement -= (sideDish - 6) * 4;
		}

		//		主菜計算
		if (mainDish >= 3 && mainDish <= 5) {
			achievement -= 0;
		} else if (mainDish < 3) {
			achievement -= (3 - mainDish) * 4;
		} else {
			achievement -= (mainDish - 5) * 4;
		}

		//		乳製品
		if (milkDish == 2) {
			achievement -= 0;
		} else if (milkDish == 1 || milkDish == 3) {
			achievement -= 4;
		} else {
			achievement -= 8;
		}

		//		果物
		if (fruitCount == 2) {
			achievement -= 0;
		} else if (fruitCount == 1 || fruitCount == 3) {
			achievement -= 4;
		} else {
			achievement -= 8;
		}
		return achievement;
	}

	//削除処理
	@PostMapping("/dishes/{id}/delete")
	public String delete(
			@PathVariable Integer id) {
		resultRepository.deleteById(id);
		return "redirect:/dishes/result";
	}

}