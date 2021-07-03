import kotlin.system.exitProcess
import kotlin.random.Random

fun main() {
    val major = mutableListOf("The Fool", "The Magician", "The High Priestess", "The Empress", "The Emperor", "The Hierophant", "The Lovers", "The Chariot",
    "Strength", "The Hermit", "Wheel of Fortune", "Justice", "The Hanged Man", "Death", "Temperance", "The Devil", "The Tower", "The Star", "The Moon", "The Sun",
    "Judgement", "The World") // 대 아르카나 카드 배열
    val pickedMajor = mutableListOf<String>()
    val pickedMinor = mutableListOf<Card>() // 소 아르카나 중복 방지 리스트 선언
    val minorNum = mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Page", "Knight", "Queen", "King") // 소 아르카나 숫자
    val minorSuit = mutableListOf("Wands", "Cups", "Swords", "Pentacles") // 소 아르카나 수트
    val direction = listOf("정방향", "역방향")
    print("어떤 아르카나를 사용합니까? (대/소) : ")
    val arcana = readLine()
    if (!(arcana == "대" || arcana == "소")) {
        println("잘못된 입력입니다. 종료합니다.")
        print("\nEnter를 눌러 종료합니다.")
        readLine()
        exitProcess(-1)
    }
    print("몇장의 카드를 뽑습니까? 정수로 입력해주세요. : ")
    try {
        var counts = readLine()!!.toInt()
        for (i in 0 until counts) {
            when (arcana) {
                "대" -> {
                    major.shuffle()
                    print("1부터 ${major.size} 사이의 수를 정수로 입력해주세요. : ")
                    try {
                        val input = readLine()!!.toInt()
                        if (!(input - 1 in major.indices)) {
                            println("잘못된 입력입니다. 종료합니다.")
                            print("\nEnter를 눌러 종료합니다.")
                            readLine()
                            exitProcess(-1)
                        }
                        pickedMajor.add("${major.removeAt(input - 1)} | ${direction[Random.nextInt(2)]}")
                    } catch (e : NumberFormatException) {
                        println("잘못된 입력입니다. 종료합니다.")
                        print("\nEnter를 눌러 종료합니다.")
                        readLine()
                        exitProcess(-1)
                    }
                }
                "소" -> {
                    minorNum.shuffle()
                    minorSuit.shuffle()
                    print("1부터 ${minorNum.size} 사이의 수를 정수로 입력해주세요. : ")
                    try {
                        val input = readLine()!!.toInt()
                        if (!(input - 1 in minorNum.indices)) {
                            println("잘못된 입력입니다. 종료합니다.")
                            print("\nEnter를 눌러 종료합니다.")
                            readLine()
                            exitProcess(-1)
                        }
                        print("1부터 ${minorSuit.size} 사이의 수를 정수로 입력해주세요. : ")
                        try {
                            val input2 = readLine()!!.toInt()
                            val struct = Card(number = minorNum[input], suit = minorSuit[input2], direction = direction[Random.nextInt(2)])
                            for (j in pickedMinor.indices) {
                                if (struct.number in pickedMinor[j].number && struct.suit in pickedMinor[j].suit) {
                                    counts++
                                    continue
                                }
                            }
                            pickedMinor.add(struct)
                        } catch (e : NumberFormatException) {
                            println("잘못된 입력입니다. 종료합니다.")
                            print("\nEnter를 눌러 종료합니다.")
                            readLine()
                            exitProcess(-1)
                        }
                    } catch (e : NumberFormatException) {
                        println("잘못된 입력입니다. 종료합니다.")
                        print("\nEnter를 눌러 종료합니다.")
                        readLine()
                        exitProcess(-1)
                    }
                }
            }
        }
    } catch (e : NumberFormatException) {
        println("잘못된 입력입니다. 종료합니다.")
        print("\nEnter를 눌러 종료합니다.")
        readLine()
        exitProcess(-1)
    }
    for (i in pickedMajor.indices) {
        println(pickedMajor[i])
    }
    for (i in pickedMinor.indices) {
        println("${pickedMinor[i].number} | ${pickedMinor[i].suit} | ${pickedMinor[i].direction}")
    }
    print("\nEnter를 눌러 종료합니다.")
    readLine()
    exitProcess(0)
}

data class Card(val number: String, val suit : String, val direction : String) // 소 아르카나 데이터 클래스