package in.co.praveenkumar.poptilesplus;

import in.co.praveenkumar.poptilesplus.MainActivity.GamePlayService;
import in.co.praveenkumar.poptilesplus.helper.Database;
import in.co.praveenkumar.poptilesplus.helper.Session;

public class AcheivementUnlocker {
	// Achievement unlocking score marks;

	// Hexadecimal mode marks
	private final int HEX_COPYPASTER = 20;
	private final int HEX_GET_IT_DONE = 50;
	private final int HEX_CODER = 100;
	private final int HEX_PRO_CODER = 200;
	private final int HEX_NINJA_PROG = 500;
	private final int HEX_LINUS = 5000;

	// Decimal mode marks
	private final int DEC_NOOB = 20;
	private final int DEC_BEGINNER = 50;
	private final int DEC_QUICKY = 100;
	private final int DEC_PRO = 200;
	private final int DEC_NINJA = 500;

	// Binary mode marks
	private final int BIN_COLLEGE = 20;
	private final int BIN_STUDIOUS = 50;
	private final int BIN_GEEK = 100;
	private final int BIN_NERD = 200;
	private final int BIN_NERDY_GEEK = 500;

	GamePlayService playService;
	Database db;

	public AcheivementUnlocker(GamePlayService playService, Database db) {
		this.playService = playService;
		this.db = db;
	}

	/**
	 * Checks and unlocks any score based achievements
	 */
	public void checkForScoreUnlocks() {
		int highscore = db.getHighscore(Session.gameMode);

		// Possible achievements unlocked already
		if (Session.score() < highscore)
			return;

		switch (Session.gameMode) {
		case Session.GAME_MODE_BIN:
			checkBinModeUnlocks();
			break;
		case Session.GAME_MODE_DEC:
			checkDecModeUnlocks();
			break;
		case Session.GAME_MODE_HEX:
			checkHexModeUnlocks();
			break;
		}
	}

	/**
	 * Checks and unlocks any game play counts based achievements
	 */
	public void checkForGameCountUnlocks() {
		playService
				.incrementAchievement(R.string.achievement_getting_started__level_1);
		playService
				.incrementAchievement(R.string.achievement_getting_started__level_2);
		playService
				.incrementAchievement(R.string.achievement_getting_started__level_3);

		playService
				.incrementAchievement(R.string.achievement_getting_addicted__level_1);
		playService
				.incrementAchievement(R.string.achievement_getting_addicted__level_2);
		playService
				.incrementAchievement(R.string.achievement_getting_addicted__level_3);

		playService
				.incrementAchievement(R.string.achievement_addicted__level_1);
		playService
				.incrementAchievement(R.string.achievement_addicted__level_2);
		playService
				.incrementAchievement(R.string.achievement_addicted__level_3);

		playService.incrementAchievement(R.string.achievement_livein__level_1);
		playService.incrementAchievement(R.string.achievement_livein__level_2);
		playService.incrementAchievement(R.string.achievement_livein__level_3);

		playService.incrementAchievement(R.string.achievement_no_life__level_1);
		playService.incrementAchievement(R.string.achievement_no_life__level_2);
		playService.incrementAchievement(R.string.achievement_no_life__level_3);

		switch (Session.gameMode) {
		case Session.GAME_MODE_DEC:
			playService
					.incrementAchievement(R.string.achievement_layman__level_1);
			playService
					.incrementAchievement(R.string.achievement_layman__level_2);
			playService
					.incrementAchievement(R.string.achievement_layman__level_3);
			playService
					.incrementAchievement(R.string.achievement_layman__level_4);
			playService
					.incrementAchievement(R.string.achievement_layman__level_5);
			playService
					.incrementAchievement(R.string.achievement_layman__level_6);
			playService.incrementAchievement(R.string.achievement_feynman);

			break;
		case Session.GAME_MODE_BIN:
			playService
					.incrementAchievement(R.string.achievement_geek__level_1);
			playService
					.incrementAchievement(R.string.achievement_geek__level_2);
			playService
					.incrementAchievement(R.string.achievement_geek__level_3);
			playService
					.incrementAchievement(R.string.achievement_geek__level_4);
			playService
					.incrementAchievement(R.string.achievement_geek__level_5);
			playService
					.incrementAchievement(R.string.achievement_geek__level_6);
			playService.incrementAchievement(R.string.achievement_bill);
			break;
		case Session.GAME_MODE_HEX:

			playService
					.incrementAchievement(R.string.achievement_coder__level_1);
			playService
					.incrementAchievement(R.string.achievement_coder__level_2);
			playService
					.incrementAchievement(R.string.achievement_coder__level_3);
			playService
					.incrementAchievement(R.string.achievement_coder__level_4);
			playService
					.incrementAchievement(R.string.achievement_coder__level_5);
			playService
					.incrementAchievement(R.string.achievement_coder__level_6);
			playService.incrementAchievement(R.string.achievement_zuck);
			break;
		}
	}

	private void checkBinModeUnlocks() {
		switch (Session.score()) {
		case BIN_COLLEGE:
			playService.unLockAchievement(R.string.achievement_college);
			break;
		case BIN_STUDIOUS:
			playService.unLockAchievement(R.string.achievement_studious);
			break;
		case BIN_GEEK:
			playService.unLockAchievement(R.string.achievement_geek);
			break;
		case BIN_NERD:
			playService.unLockAchievement(R.string.achievement_nerd);
			break;
		case BIN_NERDY_GEEK:
			playService.unLockAchievement(R.string.achievement_the_nerdy_geek);
			break;
		}
	}

	private void checkDecModeUnlocks() {
		switch (Session.score()) {
		case DEC_NOOB:
			playService.unLockAchievement(R.string.achievement_noob);
			break;
		case DEC_BEGINNER:
			playService.unLockAchievement(R.string.achievement_begginer);
			break;
		case DEC_QUICKY:
			playService.unLockAchievement(R.string.achievement_quicky);
			break;
		case DEC_PRO:
			playService.unLockAchievement(R.string.achievement_pro);
			break;
		case DEC_NINJA:
			playService.unLockAchievement(R.string.achievement_ninja);
			break;
		}
	}

	private void checkHexModeUnlocks() {
		switch (Session.score()) {
		case HEX_COPYPASTER:
			playService.unLockAchievement(R.string.achievement_copy_paster);
			break;
		case HEX_GET_IT_DONE:
			playService.unLockAchievement(R.string.achievement_get_it_done);
			break;
		case HEX_CODER:
			playService.unLockAchievement(R.string.achievement_coder);
			break;
		case HEX_PRO_CODER:
			playService.unLockAchievement(R.string.achievement_pro_coder);
			break;
		case HEX_NINJA_PROG:
			playService
					.unLockAchievement(R.string.achievement_ninja_programmer);
			break;
		case HEX_LINUS:
			playService.unLockAchievement(R.string.achievement_linus_torvalds);
			break;
		}
	}

}
