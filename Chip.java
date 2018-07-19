package com_nok_e_janken_game;

/**
 * 掛金の初期値、残高を定義するクラス
 * @author 野田健介
 *
 */
public class Chip {
	/**
	 * ゲーム開始時の掛け金初期値
	 */
	private static final int FIRST_CHIP = 100;
	/**
	 * 掛金の残高
	 */
	private int chip;
	/**
	 * 掛金
	 */
	private int bet;

	/**
	 * コンストラクタ
	 * 掛金の初期化
	 */
	public Chip() {
		chip = FIRST_CHIP;
	}

	/**
	 * 現在の掛け金残高情報の表示
	 */
	public String toString() {
		return "合計：[" + chip + "]枚";
	}

	/**
	 * chip取得用getter
	 * @return 現在のチップ数
	 */
	public int getChip() {
		return chip;
	}

	/**
	 * bet取得用getter
	 * @return 掛金
	 */
	public int getBet() {
		return bet;
	}

	/**
	 * 掛金残高から掛金を設定する
	 * @param bet 掛金
	 */

	public void setBet(int bet) {
		chip = chip - bet;
		this.bet = bet;
	}

	/**
	 * 勝って再戦時に掛金を二倍にする処理
	 */
	public void doubleUpBet() {
		bet = bet * 2;
	}

	/**
	 * 勝利時に獲得チップを所持チップに加算する処理
	 */
	public void addWinBet() {
		chip += bet;
	}
}
