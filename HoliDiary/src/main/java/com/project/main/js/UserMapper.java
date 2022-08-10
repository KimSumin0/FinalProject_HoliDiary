package com.project.main.js;

public interface UserMapper {

	int join(User u);

	User getUserByID(User u);

	int naver(User u);

	int fileUpdate(User u);

	int idCheck(User u);

	int nickCheck(User u);

	int joinWithKakao(User kakaoUser);

	int idCheckWithKakao(User u);

	User loginWithKakao(User kakaoUser);



}
