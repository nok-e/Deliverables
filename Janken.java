package com_nok_e_janken_game;

import java.util.Scanner;

public class Janken {

	/**
	 * 掛金の最大値
	 */
	private static final int MAX_BET = 20;
	/**
	 * 掛金の最小値
	 */
	private static final int MINI_BET = 1;
	/**
	 * ゲーム続行選択時の続行の値
	 */
	private static final String YES = "0";
	/**
	 * ゲーム続行選択時の辞退の値
	 */
	private static final String NO = "1";
	// フィールド
	/**
	* 現在のチップ
	*/
	private Chip chip = new Chip();
	/**
	 * 自分の手
	 */
	private String choiceHand = null;
	/**
	 * 相手の手
	 */
	private String opponentHand = null;
	/**
	 * 対戦結果
	 */
	private String result = null;
	/**
	 * スキャナー
	 */
	private Scanner scanner = new Scanner(System.in);
	/**
	 * ゲーム全体の繰り返し
	 */
	private boolean isContinue = true;

	/**
	 * 残りの掛金出力
	 */
	/**
	 * ゲームの実行部分
	 */
	public void playGame() {
		try {
			while (isContinue) {
				firstGame();
				inputBet();
				jankenMain();
				askContinue();
			}
		} catch (Exception e) {
			System.out.println("想定外のエラーが発生しました。");
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private void firstGame() {
		System.out.println("******チップの枚数******");
		System.out.println(chip);
		System.out.println("*********************");
	}

	/**
	 * 掛金入力フォーム
	 */
	private void inputBet() {
		//入力結果が正しいかどうか
		boolean isCorrect = false;
		while (!isCorrect) {
			try {
				System.out.println("■BET枚数選択");
				int maxBet = Math.min(MAX_BET, chip.getChip());
				System.out.println("BETするチップ数を入力してください(最低1～最大" + maxBet + ")");
				scanner = new Scanner(System.in);
				String tmpBet = scanner.nextLine();
				if (tmpBet.matches("0+\\d")) {
					System.out.println("数値を入力してください");
				} else {
					int numBet = Integer.parseInt(tmpBet);
					//掛金が対象外の値の時
					if (numBet < MINI_BET || numBet > MAX_BET) {
						System.out.println("指定可能範囲外です。");
					} else if (numBet > chip.getChip()) {
						//掛金が手持ちのチップを超えている時
						System.out.println("BET枚数が現在の保有チップを超えています。");
					} else {
						//正しい値入力時
						chip.setBet(numBet);
						isCorrect = true;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("数値を入力してください");
			}
			System.out.println();
		}

	}

	/**
	 * 大小選択と勝負判定
	 */
	private void jankenMain() {
		boolean isWinContinue = true;
		while (isWinContinue) {
			//出す手の選択ここから
			System.out.println("■選択");
			System.out.println("どの手をだしますか？");
			System.out.println("0:グー 1:チョキ 2:パー");
			String choice = getInput();
			//出す手の選択の文字変換
			if (choice.equals("0")) {
				choiceHand = "グー";
			} else if (choice.equals("1")) {
				choiceHand = "チョキ";
			} else if (choice.equals("2")) {
				choiceHand = "パー";
			}

			//出す手の選択ここまで

			//勝敗の判定ここから
			int opponent = new java.util.Random().nextInt(3);
			if (opponent == 0) {
				opponentHand = "グー";
			} else if (opponent == 1) {
				opponentHand = "チョキ";
			} else if (opponent == 2) {
				opponentHand = "パー";
			}

			System.out.println("BET数: " + chip.getBet());
			System.out.println("あなたの手： " + choiceHand);
			System.out.println("相手の手：" + opponentHand);
			if (choiceHand.equals(opponentHand)) {
				result = "あーいこーで！！";
				isWinContinue = true;
			} else if (choiceHand.equals("グー") && opponentHand.equals("チョキ")) {
				result = "かった！";
			} else if (choiceHand.equals("チョキ") && opponentHand.equals("パー")) {
				result = "かった！";
			} else if (choiceHand.equals("パー") && opponentHand.equals("グー")) {
				result = "かった！";
			} else {
				result = "まけた、、、";
			}
			System.out.println("結果はーー");
			System.out.println("");
			System.out.println(result);
			//Big or Smallの判定ここまで

			//勝敗の判定ここから
			if (result.equals("かった！")) {
				//結果勝利時
				chip.doubleUpBet();
				System.out.println("チップ" + chip.getBet() + "枚を獲得しました。");
				System.out.println();
				System.out.println("[獲得したチップ" + chip.getBet() + "枚で勝負を続けますか？]：0：Yes 1:No");
				String continueGame = getInput();
				if (continueGame.equals(YES)) {

				} else {
					//ダブルアップに挑戦しない
					chip.addWinBet();
					isWinContinue = false;
				}
			} else if(result.equals("まけた、、、")){
				//結果敗北時
				isWinContinue = false;
			}
			//勝敗の判定ここまで
		}
	}

	/**
	 * ゲーム続行選択
	 */
	private void askContinue() {
		System.out.println("******現在のチップ枚数******");
		System.out.println(chip);
		System.out.println("************************");
		if (chip.getChip() <= 0) {
			System.out.println("チップ枚数が0になった為、ゲームを終了します。");
			isContinue = false;
			System.out.println();
			System.out.println("END");
		} else {
			System.out.println("ゲームを続けますか?: 0:Yes 1:No");
			String choice = getInput();
			if (choice.equals(NO)) {
				//ゲーム終了時
				isContinue = false;
				System.out.println();
				System.out.println("おつかれさまでしたー");
				System.out.println("また遊んでねー");
			}
		}
	}

	/**
	 * 0か1か2の入力(不正な値の場合は繰り返し入力を求める)
	 * @return　0,1,2のいずれか
	 */
	private String getInput() {
		while (true) {
			scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			if (choice.equals("0") || choice.equals("1") || (choice.equals("2"))) {
				//正しく入力された時
				return choice;
			} else {
				//不正な値の時
				System.out.println("入力された値が不正です、半角の[0],[1],[2]のいずれかを入力してください。");
			}
		}
	}
}
