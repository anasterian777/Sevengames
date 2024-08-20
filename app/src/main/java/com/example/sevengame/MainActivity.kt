import kotlin.random.Random

class Game {
    var playerScore = 0
    var pirateScore = 0
    var rounds = 0
    var mode: GameMode = GameMode.FRIEND
    var bet: Int = 0
    var krakenActive = false
    var pirateDifficulty: PirateDifficulty = PirateDifficulty.JUNGA

    fun startGame() {
        while (!isGameOver()) {
            playRound()
            rounds++
        }
        declareWinner()
    }

    fun playRound() {
        // Если кракен активен, выполняем его логику
        if (krakenActive) {
            handleKrakenIntervention()
        } else {
            // Броски костей для игрока и пирата
            val playerRoll = rollDice()
            val pirateRoll = rollDice(pirateDifficulty)

            // Сравнение бросков и начисление очков
            if (playerRoll > pirateRoll) {
                playerScore += bet
            } else if (pirateRoll > playerRoll) {
                pirateScore += bet
            }
        }
    }

    fun rollDice(difficulty: PirateDifficulty? = null): Int {
        val baseRoll = (1..6).random()
        return if (difficulty != null) {
            baseRoll + difficulty.luckFactor
        } else {
            baseRoll
        }
    }

    fun handleKrakenIntervention() {
        // Логика вмешательства кракена
        println("Кракен вмешивается! Начинается шторм!")
        // Все бросают кости два раза
        val playerRoll1 = rollDice()
        val playerRoll2 = rollDice()
        val pirateRoll1 = rollDice(pirateDifficulty)
        val pirateRoll2 = rollDice(pirateDifficulty)

        // Сравнение бросков
        val playerTotal = playerRoll1 + playerRoll2
        val pirateTotal = pirateRoll1 + pirateRoll2

        if (playerTotal > pirateTotal) {
            playerScore += bet * 2 // Удвоение очков за победу в шторме
        } else if (pirateTotal > playerTotal) {
            pirateScore += bet * 2
        }
    }

    fun isGameOver(): Boolean {
        return playerScore >= 100
        rounds >= 10
        pirateScore >= 10
    }

    fun declareWinner() {
        when {
            playerScore >= 1000 -> println("Игрок победил!")
            pirateScore >= 1000 -> println("Пират победил!")
            else -> println("Игра окончена!")
        }
        // Обновление званий в зависимости от очков
    }

    fun placeBet(amount: Int) {
        bet = amount
    }

    fun activateKraken() {
        krakenActive = true
    }
}

enum class GameMode {
    FRIEND,
    PIRATE
}

enum class PirateDifficulty(val luckFactor: Int) {
    JUNGA(0),
    PIRATE(1),
    CAPTAIN(2)
}