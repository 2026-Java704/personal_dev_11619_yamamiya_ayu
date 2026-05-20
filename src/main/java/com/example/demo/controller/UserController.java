package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	private final HttpSession session;
	private final UserRepository userRepository;

	public UserController(HttpSession session, UserRepository userRepository) {
		this.session = session;
		this.userRepository = userRepository;
	}

	//ログイン画面表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		// セッション情報を全てクリアする
		session.invalidate();

		return "login";
	}

	// ログインを実行
	@PostMapping({ "/", "/login", "/logout" })
	public String login(
			@RequestParam String email,
			@RequestParam String password,
			Model model) {
		List<String> errorList = new ArrayList<>();

		// メールアドレスが空の場合にエラーとする
		if (email == null || email.length() == 0) {
			errorList.add("メールアドレスを入力してください");
		}
		// パスワードが空の場合にエラーとする
		if (password == null || password.length() == 0) {
			errorList.add("パスワードを入力してください");
		}
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("email", email);
			model.addAttribute("password", password);

			return "login";
		}
		List<User> userList = userRepository.findByEmailAndPassword(email, password);
		if (userList == null || userList.size() == 0) {
			// 存在しなかった場合
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			return "login";
		}

		// 「/dishes/result」へのリダイレクト
		session.setAttribute("userId", userList.get(0).getUserId());
		return "redirect:/dishes/result";
	}

	//新規会員登録の表示
	@GetMapping("/users/add")
	public String create() {
		return "user";
	}

	//新規会員登録
	@PostMapping("/users/add")
	public String store(
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") Integer age,
			@RequestParam(defaultValue = "") Integer gender,
			Model model) {
		List<String> addErrorList = new ArrayList<>();
		if (email == null || email.length() == 0) {
			addErrorList.add("メールアドレスを入力してください");
		}
		if (name == null || name.length() == 0) {
			addErrorList.add("名前を入力してください");
		}
		if (password == null || password.length() == 0) {
			addErrorList.add("パスワードを入力してください");
		}
		if (age == null) {
			addErrorList.add("年齢を入力してください");
		}
		if (gender == null) {
			addErrorList.add("性別を選択してください");
		}
		if (addErrorList.size() > 0) {
			model.addAttribute("addErrorList", addErrorList);
			model.addAttribute("email", email);
			model.addAttribute("name", name);
			model.addAttribute("password", password);
			model.addAttribute("age", age);
			model.addAttribute("gender", gender);

			return "user";
		}

		User user = new User(email, name, password, age, gender);
		userRepository.save(user);
		return "redirect:/login";

	}
}
